"""Generates the application icon of Hermes 2: a bust of Hermes wearing the winged petasos, in marble tones on the
navy tile of the brand.

The bust is a cast in the Statens Museum for Kunst, Copenhagen ("Hermes med petasos", KAS1636, unknown artist), from
a public-domain photograph on Wikimedia Commons (hermes-bust-smk-kas1636.jpg; see CREDITS.md). hermes-bust.png is
its cut-out (made once by segment_bust.py, which needs OpenCV); this script only needs Pillow.

Writes icons/app/hlom<size>.png, the launcher icons (hlom.ico, hlom.icns, hlom.xpm), the splash screen (splash.bmp
for the launcher, with its text and logo, and text-free backgrounds for HlomSplashHandler; the text uses the Segoe UI
fonts of Windows), the logos of the HLOM Guide (help/img) and redraws the logo in the wizard images of the Windows
installer. Also draws the Look Up icon (icons/actions/lookup.png). Run with: python icons-src/gen_icons.py.

The .ico holds uncompressed (BMP) images only: the Eclipse launcher icon replacement of Tycho can't read
PNG-compressed icons and silently leaves the Eclipse icon in Hermes2.exe when it finds them.
"""

import math
import os
import struct
from io import BytesIO

from PIL import Image, ImageChops, ImageDraw, ImageEnhance, ImageOps

EDITOR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
INSTALLER = os.path.join(EDITOR, '..', '..', 'releng', 'edu.louisiana.caai.lom.product', 'installer')

NAVY = (30, 41, 59, 255)
WHITE = (255, 255, 255, 255)
RED = (206, 24, 30, 255)

S = 1024  # drawing resolution, scaled down for every output size


HERE = os.path.dirname(os.path.abspath(__file__))
BUST = os.path.join(HERE, 'hermes-bust.png')
_bust = None


def bust():
    """The cut-out of the bust (RGBA), loaded once."""
    global _bust
    if _bust is None:
        _bust = Image.open(BUST).convert('RGBA')
    return _bust


def marble(image):
    """The bust in the tones of the brand: shadows in slate blue, highlights in warm marble white."""
    gray = ImageOps.autocontrast(image.convert('L'), cutoff=1)
    gray = ImageEnhance.Contrast(gray).enhance(1.15)
    toned = ImageOps.colorize(gray, black=(70, 84, 110), white=(250, 247, 240), mid=(176, 182, 192)).convert('RGBA')
    toned.putalpha(image.getchannel('A'))
    return toned


def logo(size, layout=None):
    """
    The logo as an RGBA image of size x size pixels, composed for the display size layout (default: size; the
    @2x images of high resolution displays are drawn at twice the size with the composition of their 1x size). From
    48 pixels the whole bust rises from the bottom of the tile; 16 and 32 pixel icons show the head with the petasos,
    filling the tile, so that they stay recognizable.
    """
    layout = layout or size
    tile_box = (16, 16, S - 17, S - 17)
    im = Image.new('RGBA', (S, S), (0, 0, 0, 0))
    ImageDraw.Draw(im).rounded_rectangle(tile_box, radius=210, fill=NAVY)
    source = bust()
    whole = layout >= 48
    if not whole:
        # Down to the chin.
        source = source.crop((0, 0, source.width, int(source.height * 0.79)))
    figure = marble(source)
    height = 900 if whole else 960
    width = round(figure.width * height / figure.height)
    figure = figure.resize((width, height), Image.LANCZOS)
    layer = Image.new('RGBA', (S, S), (0, 0, 0, 0))
    layer.alpha_composite(figure, ((S - width) // 2 + 10, S - height if whole else 40))
    # Clipped to the tile.
    clip = Image.new('L', (S, S), 0)
    ImageDraw.Draw(clip).rounded_rectangle(tile_box, radius=210, fill=255)
    layer.putalpha(ImageChops.multiply(layer.getchannel('A'), clip))
    im.alpha_composite(layer)
    return im.resize((size, size), Image.LANCZOS)


def ico_bmp(im):
    """An icon directory entry image in BMP format: 32-bit BGRA rows bottom-up, followed by the AND mask."""
    w, h = im.size
    header = struct.pack('<IiiHHIIiiII', 40, w, h * 2, 1, 32, 0, 0, 0, 0, 0, 0)
    px = im.load()
    rows = bytearray()
    for y in range(h - 1, -1, -1):
        for x in range(w):
            r, g, b, a = px[x, y]
            rows += bytes((b, g, r, a))
    mask_stride = ((w + 31) // 32) * 4
    mask = bytearray()
    for y in range(h - 1, -1, -1):
        row = bytearray(mask_stride)
        for x in range(w):
            if px[x, y][3] < 128:
                row[x // 8] |= 0x80 >> (x % 8)
        mask += row
    return header + bytes(rows) + bytes(mask)


def ico_bmp8(im):
    """An 8-bit palette BMP entry for the classic launcher icon formats (transparency through the AND mask)."""
    w, h = im.size
    flat = Image.new('RGBA', im.size, NAVY)
    flat.alpha_composite(im)
    pal = flat.convert('RGB').quantize(colors=256, method=Image.Quantize.MEDIANCUT)
    colors = pal.getpalette()[:768]
    colors += [0] * (768 - len(colors))
    header = struct.pack('<IiiHHIIiiII', 40, w, h * 2, 1, 8, 0, 0, 0, 0, 256, 0)
    palette = bytearray()
    for i in range(256):
        r, g, b = colors[3 * i:3 * i + 3]
        palette += bytes((b, g, r, 0))
    stride = (w + 3) // 4 * 4
    idx = pal.load()
    alpha = im.load()
    rows = bytearray()
    for y in range(h - 1, -1, -1):
        row = bytearray(stride)
        for x in range(w):
            row[x] = idx[x, y]
        rows += row
    mask_stride = ((w + 31) // 32) * 4
    mask = bytearray()
    for y in range(h - 1, -1, -1):
        row = bytearray(mask_stride)
        for x in range(w):
            if alpha[x, y][3] < 128:
                row[x // 8] |= 0x80 >> (x % 8)
        mask += row
    return header + bytes(palette) + bytes(rows) + bytes(mask)


def write_ico(path, images):
    """images: list of (Image, bits) with bits 32 or 8."""
    entries = [(im, ico_bmp(im) if bits == 32 else ico_bmp8(im), bits) for im, bits in images]
    out = BytesIO()
    out.write(struct.pack('<HHH', 0, 1, len(entries)))
    offset = 6 + 16 * len(entries)
    for im, data, bits in entries:
        w, h = im.size
        out.write(struct.pack('<BBBBHHII', w % 256, h % 256, 0 if bits == 32 else 0, 0, 1, bits, len(data), offset))
        offset += len(data)
    for _, data, _ in entries:
        out.write(data)
    with open(path, 'wb') as f:
        f.write(out.getvalue())


def redraw(bmp, box):
    """Replaces the logo occupying box (left, top, right, bottom, inclusive) in a 24-bit bitmap."""
    im = Image.open(bmp).convert('RGB')
    left, top, right, bottom = box
    background = im.getpixel((left - 3, (top + bottom) // 2))
    ImageDraw.Draw(im).rectangle((left - 1, top - 1, right + 1, bottom + 1), fill=background)
    size = right - left + 1
    im.paste(logo(size), (left, top), logo(size))
    im.save(bmp)


SLATE = (71, 85, 105, 255)


def lookup(size):
    """The Look Up icon: a plain magnifying glass (no plus or minus, which would read as zoom in or out)."""
    im = Image.new('RGBA', (S, S), (0, 0, 0, 0))
    d = ImageDraw.Draw(im)
    cx, cy, r, ring = 410, 410, 270, 100
    d.ellipse((cx - r, cy - r, cx + r, cy + r), outline=SLATE, width=ring)
    # The handle, from the rim at 45 degrees to the lower right corner, with a round end.
    start = cx + (r - 20) * math.cos(math.pi / 4)
    d.line((start, start, 900, 900), fill=SLATE, width=150)
    d.ellipse((900 - 75, 900 - 75, 900 + 75, 900 + 75), fill=SLATE)
    return im.resize((size, size), Image.LANCZOS)


FONTS = os.path.join(os.environ.get('WINDIR', r'C:\Windows'), 'Fonts')
INK = (30, 41, 59)
MUTED = (71, 85, 105)
FAINT = (148, 163, 184)

# The splash screen's text in 1x pixels: (text, font file, left, baseline, ink width, color). The font size is
# chosen so that the text has that width. Keep in sync with HlomSplashHandler.LINES, which draws the same text over
# the background images on Windows (sharper than text in an image).
SPLASH_TEXT = [
    ('Hermes 2', 'seguisb.ttf', 41, 156, 167, INK),
    ('MedBiquitous Editor', 'seguisb.ttf', 41, 198, 194, INK),
    ('Standards for health professions education', 'segoeui.ttf', 41, 220, 296, MUTED),
    ('Ravi Teja Bhupatiraju', 'seguisb.ttf', 41, 298, 103, MUTED),
    ('University of Louisiana at Lafayette  \u00b7  Center for Applied Artificial Intelligence (CAAI)', 'segoeui.ttf',
     41, 313, 337, FAINT),
]


def fitted_font(file, text, width):
    """
    The font of a file in the whole pixel size whose ink width is closest to the given width. Whole pixel sizes
    keep the glyph stems on the pixel grid (fractional sizes blur small text).
    """
    from PIL import ImageFont

    def ink_width(size):
        left, _, right, _ = ImageFont.truetype(os.path.join(FONTS, file), size).getbbox(text, anchor='ls')
        return right - left

    size = min(range(6, 200), key=lambda size: abs(ink_width(size) - width))
    return ImageFont.truetype(os.path.join(FONTS, file), size)


def splash(scale, text=True, logo_image=True):
    """
    The splash screen (520 x 320 at 1x), optionally without its text and logo; the progress bar and message go in
    the blank band.
    """
    def box(x0, y0, x1, y1):
        return (x0 * scale, y0 * scale, (x1 + 1) * scale - 1, (y1 + 1) * scale - 1)

    im = Image.new('RGBA', (520 * scale, 320 * scale), (255, 255, 255, 255))
    d = ImageDraw.Draw(im)
    d.rectangle(box(0, 0, 519, 3), fill=RED)
    d.rectangle(box(388, 4, 519, 235), fill=(244, 246, 249))
    # A large, faint cross in the side panel (cut off by the edge).
    d.rectangle(box(437, 38, 499, 197), fill=(232, 236, 242))
    d.rectangle(box(401, 87, 519, 147), fill=(232, 236, 242))
    d.rectangle(box(0, 236, 519, 236), fill=(226, 232, 240))
    d.rectangle(box(40, 168, 76, 170), fill=RED)
    if logo_image:
        im.alpha_composite(logo(60 * scale), (42 * scale, 50 * scale))
    for line, file, left, baseline, width, color in SPLASH_TEXT if text else []:
        font = fitted_font(file, line, width * scale)
        ink_left = font.getbbox(line, anchor='ls')[0]
        d.text((left * scale - ink_left, baseline * scale), line, font=font, fill=color, anchor='ls')
    return im.convert('RGB')


def write_xpm(path, image):
    """Writes an RGBA image as an XPM (the launcher icon format on Linux), with transparent pixels as None."""
    rgba = image.convert('RGBA')
    opaque = rgba.convert('RGB').quantize(colors=200, method=Image.Quantize.MEDIANCUT)
    palette = opaque.getpalette()
    alpha = rgba.getchannel('A')
    chars = [chr(c) for c in range(35, 127) if chr(c) not in '"\\']
    codes = [a + b for a in chars for b in chars]
    used = sorted(set(opaque.getdata()))
    code = {index: codes[i + 1] for i, index in enumerate(used)}
    width, height = rgba.size
    lines = ['/* XPM */', 'static char *hermes2[] = {', '"%d %d %d 2",' % (width, height, len(used) + 1),
             '"%s c None",' % codes[0]]
    for index in used:
        r, g, b = palette[index * 3:index * 3 + 3]
        lines.append('"%s c #%02X%02X%02X",' % (code[index], r, g, b))
    pixels, transparency = list(opaque.getdata()), list(alpha.getdata())
    for y in range(height):
        row = ''.join(codes[0] if transparency[y * width + x] < 128 else code[pixels[y * width + x]]
                      for x in range(width))
        lines.append('"%s"%s' % (row, ',' if y < height - 1 else ''))
    lines.append('};')
    with open(path, 'w', encoding='ascii', newline='\n') as out:
        out.write('\n'.join(lines) + '\n')


def write_launcher_icons(app):
    """The launcher icons of macOS (.icns, all sizes up to 1024) and Linux (.xpm, 256 x 256)."""
    logo(1024).save(os.path.join(app, 'hlom.icns'))
    write_xpm(os.path.join(app, 'hlom.xpm'), logo(256))


def main():
    # The launcher shows splash.bmp until Java runs; then HlomSplashHandler shows the backgrounds (with the logo) in
    # the display's resolution and draws the text at the same places. The launcher's image has the text and the logo
    # too (blurred on high resolution displays, which enlarge it), so that the splash screen is never empty.
    splash(1).save(os.path.join(EDITOR, 'splash.bmp'))
    splash(1, text=False).save(os.path.join(EDITOR, 'icons', 'app', 'splash-background.png'))
    splash(2, text=False).save(os.path.join(EDITOR, 'icons', 'app', 'splash-background@2x.png'))

    actions = os.path.join(EDITOR, 'icons', 'actions')
    lookup(16).save(os.path.join(actions, 'lookup.png'))
    lookup(32).save(os.path.join(actions, 'lookup@2x.png'))

    app = os.path.join(EDITOR, 'icons', 'app')
    for size in (16, 32, 48, 64, 128, 256):
        logo(size).save(os.path.join(app, 'hlom%d.png' % size))
        # For high resolution displays (SWT and JFace load name@2x.png).
        logo(size * 2, layout=size).save(os.path.join(app, 'hlom%d@2x.png' % size))
    # The image formats of the Eclipse launcher, all of which Tycho replaces.
    write_ico(os.path.join(app, 'hlom.ico'),
              [(logo(256), 32), (logo(48), 32), (logo(32), 32), (logo(16), 32),
               (logo(48), 8), (logo(32), 8), (logo(16), 8)])
    write_launcher_icons(app)

    help_images = os.path.join(EDITOR, 'help', 'img')
    logo(64).save(os.path.join(help_images, 'hlom.png'))
    logo(16).save(os.path.join(help_images, 'hlom16.png'))
    logo(32, layout=16).save(os.path.join(help_images, 'hlom16@2x.png'))
    logo(128, layout=64).save(os.path.join(help_images, 'hlom@2x.png'))

    redraw(os.path.join(INSTALLER, 'wizard.bmp'), (39, 86, 124, 171))
    redraw(os.path.join(INSTALLER, 'wizard-small.bmp'), (4, 6, 50, 51))


if __name__ == '__main__':
    main()
