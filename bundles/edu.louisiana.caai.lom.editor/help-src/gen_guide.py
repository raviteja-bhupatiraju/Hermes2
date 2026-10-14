# Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
"""Generates the in-app HLOM Guide (help/guide.html, guide.css, guide.js, img/) of the editor bundle from
docs/hlom-spec-notes.md and model support/field-help.properties.  Usage (from the repository root): python bundles/edu.louisiana.caai.lom.editor/help-src/gen_guide.py . bundles/edu.louisiana.caai.lom.editor/help
Requires Pillow (menu icons)."""
import html, os, re, shutil, sys

REPO, OUT = sys.argv[1], sys.argv[2]
EDITOR = os.path.join(REPO, 'bundles', 'edu.louisiana.caai.lom.editor')
SPEC_MD = open(os.path.join(REPO, 'docs', 'hlom-spec-notes.md'), encoding='utf-8').read()
HELP_PROPS = os.path.join(REPO, 'bundles', 'edu.louisiana.caai.lom.model', 'src', 'edu', 'louisiana', 'caai', 'lom', 'model', 'support', 'field-help.properties')
PDF_NAME = 'HealthcareLOMSpecificationv1_1_30Sep2009.pdf'

C = dict(ACCENT='#CE181E', ACCENT_L='#FBEAEA', TEAL='#0E7C7B', TEAL_L='#E3F2F1', INK='#1E293B', SLATE='#475569',
         MUTED='#94A3B8', AMBER='#D97706', AMBER_L='#FDF3E3', BG='#F4F6F9', BAR='#EEF2F6', BORDER='#DDE3EA',
         SURFACE='#FFFFFF', CODE_BG='#F8FAFC')

e = lambda s: html.escape(s, quote=True)


# ---------------------------------------------------------------- markdown helpers

def inline(text):
    """Inline Markdown: code, bold, emphasis, links (input is raw text)."""
    out, pos = [], 0
    for m in re.finditer(r'`([^`]+)`', text):
        out.append(_inline_plain(text[pos:m.start()]))
        out.append('<code>' + e(m.group(1)) + '</code>')
        pos = m.end()
    out.append(_inline_plain(text[pos:]))
    return ''.join(out)


def _inline_plain(text):
    s = e(text)
    s = re.sub(r'\*\*(.+?)\*\*', r'<strong>\1</strong>', s)
    s = re.sub(r'(?<![\w*])\*([^*\s][^*]*?)\*(?![\w*])', r'<em>\1</em>', s)
    s = re.sub(r'\[([^\]]+)\]\(([^)\s]+)\)', r'<a href="\2">\1</a>', s)
    s = re.sub(r'(?<!["=>])(https?://[^\s<,;)]+[^\s<,;).])', r'<a href="\1">\1</a>', s)
    return s


def parse_table(lines):
    rows = [[c.strip() for c in l.strip().strip('|').split('|')] for l in lines]
    return rows[0], [r for r in rows[2:]]


def md_to_html(md):
    """Small Markdown converter: headings, paragraphs, nested lists, tables, code fences, inline markup."""
    lines, out, i = md.split('\n'), [], 0
    while i < len(lines):
        line = lines[i]
        if line.startswith('```'):
            j = i + 1
            while not lines[j].startswith('```'):
                j += 1
            out.append(code_block('\n'.join(lines[i + 1:j]), line[3:].strip()))
            i = j + 1
        elif re.match(r'#{1,6} ', line):
            level = len(line) - len(line.lstrip('#'))
            out.append('<h%d>%s</h%d>' % (level, inline(line[level + 1:]), level))
            i += 1
        elif line.startswith('|'):
            j = i
            while j < len(lines) and lines[j].startswith('|'):
                j += 1
            head, rows = parse_table(lines[i:j])
            out.append(table_html(head, rows))
            i = j
        elif re.match(r'\s*[-*] ', line):
            j = i
            while j < len(lines) and re.match(r'\s*[-*] ', lines[j]):
                j += 1
            out.append(list_html(lines[i:j]))
            i = j
        elif not line.strip() or line.strip() == '---':
            i += 1
        else:
            j = i
            while j < len(lines) and lines[j].strip() and not re.match(r'(#|\||```|\s*[-*] )', lines[j]):
                j += 1
            out.append('<p>' + inline(' '.join(lines[i:j])) + '</p>')
            i = j
    return '\n'.join(out)


def list_html(items):
    out, stack = [], []
    for item in items:
        indent = len(item) - len(item.lstrip())
        text = re.sub(r'^\s*[-*] ', '', item)
        if not stack or indent > stack[-1]:
            out.append('<ul>')
            stack.append(indent)
        else:
            while stack and indent < stack[-1]:
                out.append('</li></ul>')
                stack.pop()
            out.append('</li>')
        out.append('<li>' + inline(text))
    while stack:
        out.append('</li></ul>')
        stack.pop()
    return ''.join(out)


def table_html(head, rows, cls='data'):
    h = ''.join('<th>%s</th>' % inline(c) for c in head)
    b = ''.join('<tr>%s</tr>' % ''.join('<td>%s</td>' % inline(c) for c in r) for r in rows)
    return '<div class="table-wrap"><table class="%s"><thead><tr>%s</tr></thead><tbody>%s</tbody></table></div>' % (cls, h, b)


def highlight_xml(code):
    s = e(code)
    s = re.sub(r'(&lt;!--.*?--&gt;)', r'<span class="x-com">\1</span>', s, flags=re.S)

    def tag(m):
        inner = re.sub(r'([\w:.-]+)(\s*=\s*)(&quot;.*?&quot;)',
                       r'<span class="x-att">\1</span>\2<span class="x-val">\3</span>', m.group(2))
        return '<span class="x-tag">' + m.group(1) + '</span>' + inner + '<span class="x-tag">' + m.group(3) + '</span>'
    return re.sub(r'(&lt;/?[\w:.-]+)((?:(?!&gt;).)*?)(/?&gt;)', tag, s, flags=re.S)


def code_block(code, lang='xml'):
    body = highlight_xml(code) if lang in ('xml', '') and '&' not in code[:0] else e(code)
    return '<pre class="code"><span class="code-lang">%s</span><code>%s</code></pre>' % (e(lang or 'xml').upper(), body)


def section(md, title_prefix):
    """The body of the Markdown section whose heading starts with title_prefix (up to the next heading of the
    same or a higher level)."""
    m = re.search(r'^(#+) ' + re.escape(title_prefix) + r'.*$', md, re.M)
    level = len(m.group(1))
    rest = md[m.end():]
    n = re.search(r'^#{1,%d} ' % level, rest, re.M)
    return rest[:n.start()] if n else rest


def first_table(text):
    lines = [l for l in text.split('\n')]
    start = next(i for i, l in enumerate(lines) if l.startswith('|'))
    end = start
    while end < len(lines) and lines[end].startswith('|'):
        end += 1
    return parse_table(lines[start:end])


def quoted(text, label):
    m = re.search(label + r':\s*"(.+?)"\s*(?:"(.+?)")?\s*$', text, re.M)
    return (m.group(1) + (' ' + m.group(2) if m.group(2) else '')) if m else ''


# ---------------------------------------------------------------- help.properties

def load_help():
    cats, current, keys = [], None, {}
    for line in open(HELP_PROPS, encoding='utf-8'):
        line = line.rstrip('\n')
        m = re.match(r'# --- (\d+)?\s*(.+)', line)
        if m:
            current = {'num': m.group(1), 'name': m.group(2).strip(), 'items': []}
            cats.append(current)
            continue
        if not line.strip() or line.startswith('#'):
            continue
        k, v = [s.strip() for s in line.split('=', 1)]
        keys[k] = v
        if current is not None:
            current['items'].append((k, v))
    return cats, keys


CATS, HELP = load_help()

ids = set()


def anchor(i):
    assert i not in ids, 'duplicate id ' + i
    ids.add(i)
    return i


def chips(values, kind='lom'):
    return '<span class="chips">' + ''.join('<span class="chip %s">%s</span>' % (kind, e(v)) for v in values) + '</span>'


# ---------------------------------------------------------------- pages

PAGES = []


def page(pid, title, icon, subtitle, body, accent='accent'):
    PAGES.append(dict(id=pid, title=title, icon=icon, subtitle=subtitle, body=body, accent=accent))


def card(title, body, cid=None, kicker=None, cls=''):
    return ('<section class="card %s"%s data-title="%s">%s<h2>%s</h2>%s</section>'
            % (cls, ' id="%s"' % anchor(cid) if cid else '', e(title),
               '<div class="kicker">%s</div>' % e(kicker) if kicker else '', e(title), body))


# 1. Overview ------------------------------------------------------------------------------------------
LOM_CATEGORIES = [('general', 'General', 'general', 'Title, language, description, keywords, coverage'),
                  ('lifeCycle', 'Life Cycle', 'lifecycle', 'Version, status, contributors and dates'),
                  ('metaMetadata', 'Meta-Metadata', 'metametadata', 'About the record itself: schemas, creator'),
                  ('technical', 'Technical', 'technical', 'Formats, size, location, requirements, duration'),
                  ('educational', 'Educational', 'educational', 'Resource types, context, difficulty, time'),
                  ('rights', 'Rights', 'rights', 'Cost, copyright and conditions of use'),
                  ('relation', 'Relation', 'relation', 'Links to other learning objects'),
                  ('annotation', 'Annotation', 'annotation', 'Comments on educational use'),
                  ('classification', 'Classification', 'classification', 'Taxonomies: competencies, outcomes, guidelines')]


def overview():
    ns_head, ns_rows = first_table(SPEC_MD[SPEC_MD.index('Namespaces used'):])
    tiles = ''.join('<a class="tile" href="#cat-%s"><img src="img/%s.png" alt=""/><span class="tile-n">%d</span>'
                    '<b>%s</b><small>%s</small></a>' % (k, icon, n + 1, label, e(d))
                    for n, (k, label, icon, d) in enumerate(LOM_CATEGORIES))
    hc_tiles = ('<a class="tile hc" href="#healthcareEducation"><img src="img/healthcare-education.png" alt=""/>'
                '<b>Healthcare Education</b><small>Credits, audience, location, dates, disclosures</small></a>'
                '<a class="tile hc" href="#healthcareAsset"><img src="img/healthcare-asset.png" alt=""/>'
                '<b>Healthcare Asset</b><small>Image type, orientation, specimen, magnification</small></a>')
    n_hc = len([k for k in HELP if k in HC_KEYS])
    stats = ('<div class="stats"><div><b>9</b><span>LOM categories</span></div>'
             '<div><b>%d</b><span>documented elements</span></div><div class="t"><b>%d</b><span>healthcare extensions</span></div>'
             '<div><b>%d</b><span>credit acronyms</span></div></div>' % (len(HELP), n_hc, len(ACRONYMS)))
    intro = ('<p class="lead">The <b>MedBiquitous Healthcare Learning Object Metadata</b> standard (Healthcare LOM, '
             'ANSI/MEDBIQ LO.10.1-2008, v1.1 of 30 September 2009) describes healthcare and medical education '
             'content &mdash; courses, CME activities, simulations, images and other learning objects &mdash; so that '
             'it can be cataloged, searched, shared and accredited.</p>'
             '<p>It is an application profile of the IEEE Learning Object Metadata standard (IEEE 1484.12.1, XML '
             'binding 1484.12.3): every Healthcare LOM document is a valid IEEE LOM record with nine standard '
             'categories, plus the <code>hx:healthcareMetadata</code> extension for continuing-education credits, '
             'target audiences, disclosures and medical image descriptors, healthcare vocabularies for four LOM '
             'elements, and attributes for clinical terminology keywords (MeSH, SNOMED, UMLS).</p>' + stats)
    body = card('What is Healthcare LOM?', intro, cid='overview')
    body += card('Anatomy of a record',
                 '<p>A record has a single <code>&lt;lom&gt;</code> root. The editor shows one form page per '
                 'category; click a tile to see its elements.</p><div class="tiles">' + tiles + '</div>'
                 '<h3>MedBiquitous extensions</h3><div class="tiles">' + hc_tiles + '</div>', cid='anatomy')
    body += card('Minimal conformant record', code_block(MINIMAL_XML) +
                 '<p>The <a href="#page-conformance">conformance checklist</a> explains each requirement. New '
                 'documents created with <span class="ui">File &rsaquo; New &rsaquo; Healthcare LOM Document</span> '
                 'already satisfy them.</p>', cid='minimal')
    body += card('Namespaces', table_html(ns_head, ns_rows) +
                 '<p class="note">' + inline(SPEC_MD.split('\n')[20]) + '</p>', cid='namespaces')
    body += card('Healthcare requirements map',
                 '<p>How the healthcare requirements identified by MedBiquitous map to Healthcare LOM elements '
                 '(p. 10&ndash;12 of the specification).</p>' +
                 table_html(*first_table(section(SPEC_MD, '1. Metadata Requirements'))), cid='requirements')
    page('overview', 'Overview', 'overview', 'MedBiquitous Healthcare LOM at a glance', body)


MINIMAL_XML = '''<lom xmlns="http://ltsc.ieee.org/xsd/LOM"
     xmlns:hx="http://ns.medbiq.org/lom/extend/v1/">
   <general>
      <title><string language="en-US">Insulin Pump Therapy</string></title>
      <language>en-US</language>
      <keyword hx:source="MeSH" hx:id="D007328">
         <string language="en-US">Insulin</string>
      </keyword>
   </general>
   <metaMetadata>
      <metadataSchema>LOMv1.0</metadataSchema>
      <metadataSchema>HEALTHCARE_LOMv1</metadataSchema>
   </metaMetadata>
   <hx:healthcareMetadata>
      <hx:healthcareEducation>
         <hx:creditsAvailable>yes</hx:creditsAvailable>
      </hx:healthcareEducation>
   </hx:healthcareMetadata>
</lom>'''


# 2. Categories & elements -----------------------------------------------------------------------------

def elements_page():
    body = ''
    lom_cats = [c for c in CATS if c['num']]
    for (key, label, icon, _), cat in zip(LOM_CATEGORIES, lom_cats):
        intro, rows = '', ''
        for k, v in cat['items']:
            m = re.match(r'(\d+(?:\.\d+)*)\s+(.*)', v)
            num, text = (m.group(1), m.group(2)) if m else ('', v)
            if num == cat['num']:
                intro = '<p class="cat-intro" id="%s">%s</p>' % (anchor(k), hc_text(text))
                continue
            rows += ('<tr id="%s" data-key="%s"><td class="num">%s</td><td class="el"><code>%s</code></td>'
                     '<td>%s</td></tr>' % (anchor(k), k, e(num), e(display_name(k)), hc_text(text)))
        body += ('<section class="card cat" id="%s" data-title="%s"><div class="cat-head"><img src="img/%s.png" alt=""/>'
                 '<div><div class="kicker">Category %s</div><h2>%s <code class="tag">&lt;%s&gt;</code></h2></div></div>%s'
                 '<div class="table-wrap"><table class="elements"><thead><tr><th>#</th><th>Element</th><th>Description</th>'
                 '</tr></thead><tbody>%s</tbody></table></div></section>'
                 % (anchor('cat-' + key), e(label), icon, cat['num'], e(label), key, intro, rows))
    page('elements', 'Categories & elements', 'tree', 'The nine IEEE LOM categories and their elements', body)


DISPLAY = {'metaIdentifier': 'identifier', 'metaContribute': 'contribute', 'metaLanguage': 'language',
           'educationalDescription': 'description', 'educationalLanguage': 'language', 'rightsDescription': 'description',
           'taxonSource': 'source', 'creditExpirationDate': 'expirationDate'}


def display_name(k):
    return DISPLAY.get(k, k)


def hc_text(text):
    s = inline(text)
    return s.replace('HLOM:', '<span class="hlom-badge">HLOM</span>')


# 3. Healthcare extensions -----------------------------------------------------------------------------
HC_SECTIONS = ['2.1 healthcareMetadata', '2.2 healthcareEducation', '2.3 credits', '2.4 targetAudience',
               '2.5 activityLocation', '2.6 healthcareAsset', '2.7 LanguageString', '2.8 CharacterString']
HC_KEYS = set()
for c in CATS:
    if not c['num']:
        HC_KEYS.update(k for k, _ in c['items'])


def col(head, *names):
    for i, h in enumerate(head):
        if any(n.lower() in h.lower() for n in names):
            return i
    return None


def datatype_html(dt):
    m = re.match(r'Restricted \((?:\w+Type: )?(.*)\)$', dt)
    if m:
        return '<span class="dt">Restricted</span>' + chips([v.strip() for v in m.group(1).split(', ')], 'hc')
    return '<span class="dt">%s</span>' % inline(dt)


def mult_html(text):
    m = re.match(r'([^(]+?)\s*\((.*)\)$', text)
    if m:
        return '%s<div class="fine">%s</div>' % (e(m.group(1)), inline(m.group(2)))
    return e(text)


def healthcare_page():
    body = ''
    for title in HC_SECTIONS:
        text = section(SPEC_MD, title)
        name = title.split(' ', 1)[1]
        pages_ref = re.search(r'^#+ ' + re.escape(title) + r'\s*\(([^)]*)\)\s*(.*)$', SPEC_MD, re.M)
        intro = quoted(text, 'Intro') or quoted(text, 'Text')
        if not intro:
            m = re.search(r'^Intro:\s*"(.*)"', text, re.M) or re.search(r'^Verbatim:\s*"(.*)"', text, re.M)
            intro = m.group(1) if m else ''
        body_html = '<p>%s</p>' % inline(intro) if intro else ''
        if '|' in text:
            head, rows = first_table(text)
            ci, cm, cd, cdesc, creq = (col(head, 'Element'), col(head, 'Multiplicity'), col(head, 'Datatype'),
                                       col(head, 'Description'), col(head, 'Required', 'Obligation'))
            trs = ''
            for r in rows:
                el = r[ci]
                key = el.lstrip('@').split('/')[-1].lstrip('@')
                if name == 'credits' and key == 'expirationDate':
                    key = 'creditExpirationDate'
                elif el.startswith('string/@'):
                    key = 'string-language'
                elif el == 'string':
                    key = 'string'
                elif el.startswith('@'):
                    key = name + '-' + key
                rid = anchor(key) if key not in ids else anchor(name + '-' + key)
                req = r[creq] if creq is not None else ''
                badge = '<span class="req %s">%s</span>' % ('must' if req.lower().startswith('mandatory') else 'opt',
                                                            e(req.split(' ')[0] or 'n/a'))
                trs += ('<tr id="%s"><td class="el"><code>%s</code>%s</td><td class="mult">%s</td><td>%s</td>'
                        '<td>%s</td></tr>' % (rid, e(display_name(key) if key in HELP else el), badge,
                                              mult_html(r[cm]) if cm is not None else '',
                                              datatype_html(r[cd]) if cd is not None else '', inline(r[cdesc])))
                if name == 'activityLocation' and el == 'building':
                    trs += ('<tr id="%s"><td class="el"><code>a:Country</code><span class="req must">Required</span></td>'
                            '<td class="mult">1</td><td><span class="dt">CountryType</span></td><td>%s</td></tr>'
                            % (anchor('country'), inline(HELP['country'])))
            body_html += ('<div class="table-wrap"><table class="elements hc"><thead><tr><th>Element</th><th>Multiplicity</th>'
                          '<th>Datatype</th><th>Description</th></tr></thead><tbody>%s</tbody></table></div>' % trs)
        notes = [l for l in text.split('\n') if l.startswith('Note') or l.startswith('(Order') or l.startswith('Suggestion')]
        if name == 'activityLocation':
            addr = text[text.index('Inherited address'):text.index('- then')]
            body_html += '<h3>Inherited address content</h3>' + md_to_html(addr.split('\n', 1)[1])
        if name == 'credits':
            body_html += ('<h3>Recommended values</h3><p><b>accreditingBody</b></p>%s<p><b>activityCertification</b></p>%s'
                          '<p class="note">Acronyms are explained in <a href="#page-acronyms">Credit acronyms</a>.</p>'
                          % (chips(SUGG['accreditingBody']), chips(SUGG['activityCertification'])))
        for n in notes:
            if not n.startswith('Suggestion'):
                body_html += '<p class="note">%s</p>' % inline(n)
        kicker = 'Section %s%s' % (title.split(' ')[0], ' · spec ' + pages_ref.group(1) if pages_ref else '')
        sid = 'sec-' + name
        body += card(name, body_html, cid=sid, kicker=kicker, cls='hc')
    # keyword attributes
    attrs = section(SPEC_MD, 'Custom Attributes')
    head, rows = first_table(attrs)
    trs = ''.join('<tr id="%s"><td class="el"><code>%s</code></td><td>%s</td></tr>'
                  % (anchor('keyword-' + r[0].split(':')[-1] if '@' in r[0] else 'keyword-element'), e(r[0]), inline(r[4]))
                  for r in rows)
    ex = re.search(r'```xml\n(.*?)```', attrs, re.S).group(1)
    body += card('Clinical terminology keywords',
                 '<p>Medical terminologies such as MeSH, SNOMED and UMLS provide a structure for describing healthcare '
                 'topics and can facilitate indexing, cataloging and discovery of healthcare learning content. '
                 'Healthcare LOM adds two attributes to the LOM <code>keyword</code> element:</p>'
                 '<div class="table-wrap"><table class="elements hc"><thead><tr><th>Element / attribute</th><th>Description</th>'
                 '</tr></thead><tbody>' + trs + '</tbody></table></div>' + code_block(ex.strip()) +
                 '<p class="tip"><b>Tip</b> &mdash; On the General page, look up MeSH descriptors to fill the term, '
                 '<code>hx:source</code> and <code>hx:id</code> in one step.</p>', cid='mesh-keywords', cls='hc',
                 kicker='Custom attributes · spec p. 37')
    custom = section(SPEC_MD, 'Custom Elements')
    body += card('Custom elements', md_to_html(custom), cid='customElements', cls='hc', kicker='spec pp. 40-41')
    page('healthcare', 'Healthcare extensions', 'healthcare-education',
         'hx:healthcareMetadata &mdash; MedBiquitous extensions to LOM', body, accent='teal')


# 4. Vocabularies --------------------------------------------------------------------------------------
LOM_VOCABS = [
    ('1.7', 'structure', ['atomic', 'collection', 'networked', 'hierarchical', 'linear']),
    ('1.8', 'aggregationLevel', ['1', '2', '3', '4']),
    ('2.2', 'status', ['draft', 'final', 'revised', 'unavailable']),
    ('3.2.1', 'metaMetadata role', ['creator', 'validator']),
    ('4.4.1.1', 'requirement type', ['operating system', 'browser']),
    ('4.4.1.2', 'requirement name (OS)', ['pc-dos', 'ms-windows', 'macos', 'unix', 'multi-os', 'none']),
    ('4.4.1.2', 'requirement name (browser)', ['any', 'netscape communicator', 'ms-internet explorer', 'opera', 'amaya']),
    ('5.1', 'interactivityType', ['active', 'expositive', 'mixed']),
    ('5.3', 'interactivityLevel', ['very low', 'low', 'medium', 'high', 'very high']),
    ('5.4', 'semanticDensity', ['very low', 'low', 'medium', 'high', 'very high']),
    ('5.5', 'intendedEndUserRole', ['teacher', 'author', 'learner', 'manager']),
    ('5.8', 'difficulty', ['very easy', 'easy', 'medium', 'difficult', 'very difficult']),
    ('6.1', 'cost', ['yes', 'no']),
    ('6.2', 'copyrightAndOtherRestrictions', ['yes', 'no']),
    ('7.1', 'kind', ['ispartof', 'haspart', 'isversionof', 'hasversion', 'isformatof', 'hasformat', 'references',
                     'isreferencedby', 'isbasedon', 'isbasisfor', 'requires', 'isrequiredby']),
]


def vocab_page():
    text = section(SPEC_MD, '3. Custom Vocabularies')
    head, rows = first_table(text)
    cards = ''
    for r in rows:
        if r[0] == 'source':
            continue
        lom = [v.strip() for v in re.sub(r'\(printed.*?\)', '', r[2]).split(',')]
        hc = [v.strip() for v in r[3].split(',')]
        cards += ('<div class="vocab" id="%s"><div class="vocab-head"><code>%s</code><span class="path">%s</span></div>'
                  '<p>%s</p><div class="vocab-cols"><div><div class="src">LOMv1.0</div>%s</div>'
                  '<div><div class="src hc">HEALTHCARE_LOMv1</div>%s</div></div></div>'
                  % (anchor('vocab-' + r[0]), e(r[0]), e(r[4]), inline(r[1]), chips(lom), chips(hc, 'hc')))
    intro = ('<p>Healthcare LOM custom vocabularies supplement the LOM vocabularies to provide values useful for '
             'healthcare education. <b>Values from both the LOM vocabularies or the Healthcare LOM vocabularies are '
             'considered valid values for these elements.</b> Each value is written with its source:</p>'
             + code_block('<role>\n   <source>HEALTHCARE_LOMv1</source>\n   <value>reviewer</value>\n</role>') +
             '<div class="legend"><span class="chip lom">LOM value</span> source <code>LOMv1.0</code> '
             '<span class="chip hc">Healthcare value</span> source <code>HEALTHCARE_LOMv1</code></div>')
    body = card('Healthcare vocabularies', intro + cards, cid='vocabularies', kicker='spec pp. 38-40', cls='hc')
    lrows = ''.join('<tr id="%s"><td class="num">%s</td><td class="el"><code>%s</code></td><td>%s</td></tr>'
                    % (anchor('vocab-' + re.sub(r'\W+', '-', n).strip('-')), num, e(n), chips(v))
                    for num, n, v in LOM_VOCABS)
    body += card('IEEE LOM vocabularies',
                 '<p>The other vocabulary elements use the IEEE LOM values only (source <code>LOMv1.0</code>).</p>'
                 '<div class="table-wrap"><table class="elements"><thead><tr><th>#</th><th>Element</th><th>Values</th>'
                 '</tr></thead><tbody>' + lrows + '</tbody></table></div>', cid='lom-vocabularies')
    hc_lists = []
    for t in HC_SECTIONS[1:6]:
        h, rs = first_table(section(SPEC_MD, t))
        for r in rs:
            m = re.match(r'Restricted \((?:\w+Type: )?(.*)\)$', r[col(h, 'Datatype')])
            if m:
                hc_lists.append((r[col(h, 'Element')], [v.strip() for v in m.group(1).split(', ')]))
    hrows = ''.join('<tr><td class="el"><a href="#%s"><code>%s</code></a></td><td>%s</td></tr>'
                    % ('creditExpirationDate' if n == 'expirationDate' else n, e(n), chips(v, 'hc')) for n, v in hc_lists)
    body += card('Healthcare value lists',
                 '<p>Restricted healthcare elements accept exactly these values (case and spaces matter).</p>'
                 '<div class="table-wrap"><table class="elements hc"><thead><tr><th>Element</th><th>Valid values</th></tr>'
                 '</thead><tbody>' + hrows + '</tbody></table></div>', cid='value-lists', cls='hc')
    page('vocabularies', 'Vocabularies', 'classification', 'LOM and Healthcare LOM controlled values', body)


# 5. Conformance ---------------------------------------------------------------------------------------

def conformance_page():
    text = section(SPEC_MD, '5. Conformance')
    items = re.findall(r'^- (.*)$', text, re.M)
    checks = ''.join('<li class="%s"><span class="box"></span><div>%s</div></li>'
                     % ('may' if it.startswith('May') else 'shall', inline(it)) for it in items)
    req = text[text.index('**Additional Requirements**') + 28:]
    req_par = req.strip().split('\n\n')[0]
    xml = re.search(r'```xml\n(.*?)```', req, re.S).group(1).strip()
    body = card('Conformance', '<p>To be a conformant instance of Healthcare LOM, an XML document:</p>'
                '<ul class="checklist">' + checks + '</ul>', cid='conformance', kicker='spec p. 42 · verbatim')
    body += card('Additional requirements', '<p>' + inline(req_par) + '</p>' + code_block(xml) +
                 '<p class="note"><code>SCORM_CAM_v1.3</code> is only required for SCORM content packages.</p>',
                 cid='additional-requirements')
    editor_checks = [
        ('error', 'XML Schema', 'The document validates against the bundled Healthcare LOM schemas (works offline).'),
        ('error', 'metadataSchema', '<code>metaMetadata</code> declares <code>LOMv1.0</code> and <code>HEALTHCARE_LOMv1</code>.'),
        ('error', 'healthcareMetadata', '<code>hx:healthcareMetadata</code> occurs exactly once.'),
        ('error', 'Data types and values', 'Dates, date-times, ISO 8601 durations, numbers, patterns and restricted '
                  'values follow the schema data types.'),
        ('error', 'Dates', 'The credit release date is not after the credit expiration date; a live activity does not '
                  'start after it ends.'),
        ('warning', 'Credits', '<code>creditsAvailable</code> agrees with the <code>credits</code> entries that are given.'),
        ('warning', 'Disclosures', 'Off-label use and commercial support marked <i>yes</i> come with a description or '
                    'acknowledgement.'),
        ('warning', 'Languages', 'Every text has a language (or the record has a meta-metadata language).'),
        ('warning', 'Title', 'The learning object has a title.'),
        ('info', 'Best practice', 'The learning object has a description.'),
    ]
    rows = ''.join('<tr><td><img src="img/%s.png" alt="%s" class="sev"/></td><td><b>%s</b></td><td>%s</td></tr>'
                   % (s, s, e(t), d) for s, t, d in editor_checks)
    body += card('What the editor checks',
                 '<p>The <span class="ui">Validation</span> view combines XML Schema validation, model constraints and '
                 'the specification\'s requirements. Errors make a document non-conformant; warnings and infos point to '
                 'likely mistakes and best practices.</p><div class="table-wrap"><table class="elements checks"><tbody>'
                 + rows + '</tbody></table></div>', cid='editor-checks')
    issues = section(SPEC_MD, '8.4 Errors')
    body += card('Known issues in the specification', md_to_html(issues) +
                 '<p class="note">Typos in the requirements table: ' +
                 inline(re.search(r'^Typos/case issues in this table.*?: (.*)$', SPEC_MD, re.M).group(1)) + '</p>',
                 cid='spec-issues')
    page('conformance', 'Conformance checklist', 'ok', 'What makes a document a valid Healthcare LOM instance', body)


# 6. Acronyms ------------------------------------------------------------------------------------------
ACR_HEAD, ACRONYMS = first_table(section(SPEC_MD, '6. Appendix 1'))


def acronyms_page():
    kinds = {'CME', 'CE', 'CNE', 'CPE', 'CHES', 'CPD'}
    units = {'CECH', 'CEH', 'CEU'}
    rows = ''
    for a, meaning in ACRONYMS:
        tag = ('<span class="chip hc">credit type</span>' if a in kinds else
               '<span class="chip lom">credit unit</span>' if a in units else
               '<span class="chip acc">accrediting body</span>' if a in SUGG['accreditingBody'] else '')
        rows += '<tr id="%s"><td class="acr">%s</td><td>%s</td><td>%s</td></tr>' % (
            anchor('acr-' + re.sub(r'\W+', '-', a)), e(a), e(meaning), tag)
    body = card('Credit related acronyms',
                '<p>Glossary of the acronyms used to describe continuing education credit (Appendix 1). Type in the '
                'filter to narrow the list.</p><input class="filter" type="search" placeholder="Filter acronyms…" '
                'data-filter="acronyms"/><div class="table-wrap"><table class="elements" id="acronyms"><thead><tr>'
                '<th>Acronym</th><th>Meaning</th><th>Used as</th></tr></thead><tbody>' + rows + '</tbody></table></div>'
                '<p class="note">CPD (Continuing Professional Development) and RCP appear in value lists but are not '
                'defined in the appendix.</p>', cid='acronyms-list', kicker='Appendix 1 · spec pp. 44-45')
    page('acronyms', 'Credit acronyms', 'rights', 'Accrediting bodies, credit types and units', body)


# 7. Professions & specialties -------------------------------------------------------------------------

def professions_page():
    text = section(SPEC_MD, '7. Appendix 2')
    intro = re.search(r'Intro \(verbatim\): "(.*)"', text).group(1)
    prof = section(SPEC_MD, 'Health Professions')
    groups, cur = [], None
    for l in prof.split('\n'):
        if l.startswith('- '):
            cur = (l[2:], [])
            groups.append(cur)
        elif l.startswith('  - '):
            cur[1].append(l[4:])
    tree = ''.join('<div class="prof"><div class="prof-head">%s<span class="count">%d</span></div>%s</div>'
                   % (e(g), len(s), chips(s)) for g, s in groups)
    spec = [l.strip() for l in section(SPEC_MD, 'Health Specialties').split('\n') if l.strip()]
    grid = '<ul class="columns">' + ''.join('<li>%s</li>' % e(s) for s in spec) + '</ul>'
    body = card('Health professions', '<p>' + inline(intro) + '</p><p class="tip"><b>Use</b> &mdash; '
                'enter in <a href="#profession"><code>targetAudience/profession</code></a>; both levels of the '
                'hierarchy are valid values.</p><div class="profs">' + tree + '</div>', cid='professions',
                kicker='Appendix 2 · spec pp. 46-48', cls='hc')
    body += card('Health specialties', '<p>Recommended values for <a href="#specialty"><code>targetAudience/specialty'
                 '</code></a> (%d terms).</p>' % len(spec) + grid, cid='specialties', cls='hc')
    body += card('Reading levels', '<p>Recommended values for <a href="#readingLevel"><code>readingLevel</code></a>, '
                 'normally used for non-professional education:</p>' + chips(SUGG['readingLevel'], 'hc'),
                 cid='reading-levels', cls='hc')
    page('professions', 'Professions & specialties', 'educational', 'Recommended terms for the target audience', body,
         accent='teal')


# 8. Using the editor ----------------------------------------------------------------------------------
SHORTCUTS = [
    ('Documents', [('Ctrl+N', 'New Healthcare LOM document (from a template)'), ('Ctrl+O', 'Open a document'),
                   ('Ctrl+U', 'Open a document from a URI'), ('Ctrl+S', 'Save'), ('Ctrl+Shift+S', 'Save all'),
                   ('Ctrl+W', 'Close'), ('Ctrl+Shift+W', 'Close all')]),
    ('Editing', [('Ctrl+Z', 'Undo (one history for all pages and views)'), ('Ctrl+Y', 'Redo'), ('Ctrl+X / C / V', 'Cut, copy, paste'),
                 ('Delete', 'Delete the selected element'), ('Ctrl+A', 'Select all')]),
    ('Navigation & help', [('Ctrl+F6', 'Next editor'),
                           ('Ctrl+F7', 'Next view'), ('Ctrl+M', 'Maximize the active view or editor'),
                           ('F1', 'HLOM Guide for the focused field'), ('Shift+F1', 'Open the HLOM Guide'),
                           ('Ctrl+Shift+L', 'Show all key bindings')]),
]

WORKFLOWS = [
    ('new', 'Create a document from a template', [
        'Choose <span class="ui">File &rsaquo; New &rsaquo; Healthcare LOM Document</span> (<kbd>Ctrl</kbd>+<kbd>N</kbd>).',
        'Pick a template &mdash; e.g. a CME course, a live activity or a medical image asset &mdash; and enter the title '
        'and the language of the record.',
        'The new record already contains <code>LOMv1.0</code> and <code>HEALTHCARE_LOMv1</code> metadata schemas and the '
        '<code>healthcareMetadata</code> container. If enabled in the preferences, you are added as author and as '
        'meta-metadata creator.',
        'Fill the form pages; every field explains itself in a tooltip, and <kbd>F1</kbd> opens its topic in this guide.']),
    ('mesh', 'Add MeSH keywords', [
        'Open the <span class="ui">General</span> page and go to <span class="ui">Keywords</span>.',
        'Search the National Library of Medicine MeSH vocabulary by term; pick a descriptor from the results.',
        'The keyword is written with <code>hx:source="MeSH"</code> and the descriptor ID, e.g. <code>hx:id="D009203"</code>.',
        'MeSH lookup needs internet access; it can be disabled or pointed to another server in '
        '<span class="ui">Window &rsaquo; Preferences &rsaquo; Healthcare LOM &rsaquo; MeSH</span>.']),
    ('validate', 'Validate a document', [
        'Problems are listed in the <span class="ui">Validation</span> view; by default the document is validated a moment '
        'after each change (see <span class="ui">Preferences &rsaquo; Validation</span>).',
        'Click <span class="ui">Validate</span> in the tool bar to validate the active document on demand.',
        'Double-click a problem to jump to the element; errors make the document non-conformant, warnings and '
        'infos point to likely mistakes and best practices.']),
    ('xml', 'Work with the XML', [
        'The <span class="ui">XML</span> page shows the document source with syntax highlighting.',
        'Edits are applied to the model when you leave the page or save; files that cannot be fully loaded open with '
        'their original text so you can repair them.',
        'The <span class="ui">Tree</span> page and the <span class="ui">Outline</span> and <span class="ui">Properties</span> '
        'views show the complete structure, including custom extension elements.']),
    ('library', 'Organize your library', [
        'The library collects the Healthcare LOM documents in your library folder '
        '(<span class="ui">Preferences &rsaquo; Healthcare LOM</span>).',
        'Search and filter records by title, keyword, credit type or audience and open them with a double-click.']),
    ('prefs', 'Set your preferences', [
        'Open <span class="ui">Window &rsaquo; Preferences</span>.',
        '<b>Healthcare LOM</b> &mdash; default language of new documents, your name, organization and email (written '
        'as a vCard), and the library folder.',
        '<b>Validation</b> &mdash; validate while typing, the delay, and whether informational hints are shown.',
        '<b>MeSH</b> &mdash; enable the lookup and set the server address.']),
]


def kbd(seq):
    return ' '.join('+'.join('<kbd>%s</kbd>' % e(k) for k in part.split('+')) if part != '/' else '/'
                    for part in seq.split(' '))


def using_page():
    tbls = ''.join('<div class="keys"><h3>%s</h3><table class="shortcuts"><tbody>%s</tbody></table></div>'
                   % (e(g), ''.join('<tr><td>%s</td><td>%s</td></tr>' % (kbd(k), e(d)) for k, d in items))
                   for g, items in SHORTCUTS)
    body = card('Keyboard shortcuts', '<div class="keys-grid">' + tbls + '</div>', cid='shortcuts')
    for wid, title, steps in WORKFLOWS:
        body += card(title, '<ol class="steps">' + ''.join('<li>%s</li>' % s for s in steps) + '</ol>',
                     cid='howto-' + wid, kicker='How to')
    body += card('Pages of the editor', '<div class="table-wrap"><table class="elements"><tbody>' + ''.join(
        '<tr><td><img src="img/%s.png" class="sev" alt=""/></td><td><b>%s</b></td><td>%s</td></tr>' % (i, n, d) for i, n, d in [
            ('overview', 'Overview', 'Summary of the record, completeness and quick links.'),
            ('general', 'General … Classification', 'One form page per IEEE LOM category.'),
            ('healthcare-education', 'Healthcare Education', 'Credits, target audience, location, dates and disclosures.'),
            ('healthcare-asset', 'Healthcare Asset', 'Descriptors of medical images and other assets.'),
            ('tree', 'Tree', 'The complete element tree, including custom elements.'),
            ('xml', 'XML', 'The XML source with syntax highlighting.')]) + '</tbody></table></div>', cid='editor-pages')
    page('using', 'Using the editor', 'technical', 'Shortcuts and step-by-step workflows', body)


SUGG = {
    'accreditingBody': [v.strip() for v in re.search(r'- accreditingBody: (.*)', SPEC_MD).group(1).split(',')],
    'activityCertification': [v.strip() for v in re.search(r'- activityCertification: (.*)', SPEC_MD).group(1).split(',')],
    'readingLevel': ['grade %d' % i for i in range(1, 13)] + ['above grade 12'],
}

overview()
elements_page()
healthcare_page()
vocab_page()
conformance_page()
acronyms_page()
professions_page()
using_page()

missing = [k for k in HELP if k not in ids]
assert not missing, 'help keys without anchor: %s' % missing

# ---------------------------------------------------------------- assemble
nav = ''.join('<a class="nav-item %s" href="#page-%s" data-page="%s"><img src="img/%s.png" alt=""/><span>%s</span></a>'
              % (p['accent'], p['id'], p['id'], p['icon'], e(p['title'])) for p in PAGES)
pages = ''.join('<article class="page %s" id="page-%s" data-title="%s"><header class="page-head"><div class="crumb">'
                'HLOM Guide</div><h1>%s</h1><p>%s</p></header>%s</article>'
                % (p['accent'], p['id'], e(p['title']), e(p['title']), p['subtitle'], p['body']) for p in PAGES)

HTML = '''<!DOCTYPE html>
<html lang="en"><head><meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<title>HLOM Guide</title>
<link rel="stylesheet" href="guide.css"/>
</head>
<body>
<!-- Generated from docs/hlom-spec-notes.md and model support/field-help.properties. Specification content Copyright MedBiquitous
     Consortium, used under the MedBiquitous XML Public License. -->
<header class="top">
  <a class="brand" href="#page-overview"><img src="img/hlom.png" alt=""/><span><b>HLOM Guide</b><small>MedBiquitous Healthcare LOM v1.1</small></span></a>
  <div class="search"><input id="q" type="search" placeholder="Search elements, values, acronyms…  ( / )" autocomplete="off"/></div>
  <a class="pdf" href="%(pdf)s" title="Open the specification (PDF)">Specification PDF</a>
</header>
<div class="layout">
  <nav class="side"><div class="side-title">Contents</div>%(nav)s
    <div class="side-title sub">On this page</div><div id="toc"></div>
    <div class="side-foot">Hermes 2: MedBiquitous Editor<br/>Ravi Teja Bhupatiraju<br/>UL Lafayette &middot; Center for Applied Artificial Intelligence (CAAI)</div></nav>
  <main id="main">
    <article class="page" id="page-search"><header class="page-head"><div class="crumb">HLOM Guide</div><h1>Search</h1><p id="search-summary"></p></header><div id="results"></div></article>
    %(pages)s
    <footer class="foot">Specification content &copy; MedBiquitous Consortium (ANSI/MEDBIQ LO.10.1-2008), used under the MedBiquitous XML Public License.
    IEEE LOM descriptions summarize IEEE 1484.12.1.</footer>
  </main>
</div>
<script src="guide.js"></script>
</body></html>
''' % dict(nav=nav, pages=pages, pdf=PDF_NAME)

CSS = open(os.path.join(os.path.dirname(os.path.abspath(__file__)), 'guide.css.tmpl'), encoding='utf-8').read()
for k, v in C.items():
    CSS = CSS.replace('$' + k + ';', v + ';').replace('$' + k + ' ', v + ' ').replace('$' + k + ')', v + ')')
assert '$' not in CSS, re.findall(r'\$\w+', CSS)

os.makedirs(os.path.join(OUT, 'img'), exist_ok=True)
open(os.path.join(OUT, 'guide.html'), 'w', encoding='utf-8', newline='\n').write(HTML)
open(os.path.join(OUT, 'guide.css'), 'w', encoding='utf-8', newline='\n').write(
    '/* Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. Generated by gen_guide.py. */\n' + CSS)
shutil.copy(os.path.join(os.path.dirname(os.path.abspath(__file__)), 'guide.js'), os.path.join(OUT, 'guide.js'))
for p in {p['icon'] for p in PAGES} | {'general', 'lifecycle', 'metametadata', 'technical', 'educational', 'rights',
                                       'relation', 'annotation', 'classification', 'healthcare-education',
                                       'healthcare-asset', 'overview', 'tree', 'xml'}:
    src = os.path.join(EDITOR, 'icons', 'pages', p + '@2x.png')
    if not os.path.exists(src):
        src = os.path.join(EDITOR, 'icons', 'actions', p + '@2x.png')
    shutil.copy(src, os.path.join(OUT, 'img', p + '.png'))
for p in ('error', 'warning', 'info'):
    shutil.copy(os.path.join(EDITOR, 'icons', 'actions', p + '@2x.png'), os.path.join(OUT, 'img', p + '.png'))
shutil.copy(os.path.join(EDITOR, 'icons', 'app', 'hlom64.png'), os.path.join(OUT, 'img', 'hlom.png'))
shutil.copy(os.path.join(REPO, 'xsd', PDF_NAME), os.path.join(OUT, PDF_NAME))
print('pages', len(PAGES), 'anchors', len(ids), 'bytes', len(HTML))

# ---------------------------------------------------------------- menu icons (16 px + @2x)
from PIL import Image, ImageDraw


def pdf_icon(scale):
    s = 16 * scale
    im = Image.new('RGBA', (s * 4, s * 4), (0, 0, 0, 0))
    d = ImageDraw.Draw(im)
    k = 4 * scale
    d.polygon([(3 * k, 1 * k), (10 * k, 1 * k), (13 * k, 4 * k), (13 * k, 15 * k), (3 * k, 15 * k)], fill='#FFFFFF',
              outline='#475569', width=k)
    d.line([(10 * k, 1 * k), (10 * k, 4 * k), (13 * k, 4 * k)], fill='#475569', width=k)
    d.rounded_rectangle([1 * k, 8 * k, 11 * k, 13 * k], radius=k, fill='#CE181E')
    d.rectangle([3 * k, 10 * k, 9 * k, 10.8 * k], fill='#FFFFFF')
    return im.resize((s, s), Image.LANCZOS)


for scale, suffix in ((1, ''), (2, '@2x')):
    pdf_icon(scale).save(os.path.join(OUT, 'img', 'pdf16%s.png' % suffix))
    shutil.copy(os.path.join(EDITOR, 'icons', 'actions', 'translate%s.png' % suffix), os.path.join(OUT, 'img', 'web16%s.png' % suffix))
shutil.copy(os.path.join(EDITOR, 'icons', 'app', 'hlom16.png'), os.path.join(OUT, 'img', 'hlom16.png'))
shutil.copy(os.path.join(EDITOR, 'icons', 'app', 'hlom32.png'), os.path.join(OUT, 'img', 'hlom16@2x.png'))
