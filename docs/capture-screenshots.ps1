# Captures screenshots of every editor page of Hermes 2.
#
# Usage (from the repository root, after `mvnw.cmd clean verify`):
#   powershell -ExecutionPolicy Bypass -File docs\capture-screenshots.ps1 [-Document samples\insulin-pump-therapy-course.xml]
#   powershell -ExecutionPolicy Bypass -File docs\capture-screenshots.ps1 -StartPage   (start page only, no document)
#
# Starts the built Windows product with the system property hlom.screenshots; the editor then shows each of
# its pages in turn and announces it in <OutDir>\current. This script captures the window with the Win32
# PrintWindow API (which also works when the workstation is locked) and saves one PNG per page.

param(
    [string]$Document = "samples\insulin-pump-therapy-course.xml",
    [string]$OutDir = "docs\screenshots",
    [int]$TimeoutSeconds = 180,
    [switch]$StartPage
)

$ErrorActionPreference = "Stop"
Add-Type -AssemblyName System.Drawing
Add-Type @"
using System;
using System.Runtime.InteropServices;
public static class HlomCapture {
    [DllImport("user32.dll")] public static extern bool SetProcessDPIAware();
    [DllImport("user32.dll")] public static extern bool PrintWindow(IntPtr hWnd, IntPtr hdc, uint flags);
    [DllImport("user32.dll")] public static extern bool GetWindowRect(IntPtr hWnd, out RECT rect);
    [StructLayout(LayoutKind.Sequential)] public struct RECT { public int Left, Top, Right, Bottom; }
}
"@
[HlomCapture]::SetProcessDPIAware() | Out-Null

$root = Split-Path -Parent $PSScriptRoot
$exe = Get-ChildItem -Path (Join-Path $root "releng\edu.louisiana.caai.lom.product\target\products") -Recurse -Filter Hermes2.exe |
    Where-Object { $_.FullName -match "win32" } | Select-Object -First 1
if (-not $exe) { throw "Hermes2.exe not found - build the product first (mvnw.cmd clean verify)." }
$docPath = (Resolve-Path (Join-Path $root $Document)).Path
$out = Join-Path $root $OutDir
if (-not $StartPage -and (Test-Path $out)) { Remove-Item -Recurse -Force $out }
New-Item -ItemType Directory -Force -Path $out | Out-Null

function Save-Window([IntPtr]$handle, [string]$file) {
    $rect = New-Object HlomCapture+RECT
    [HlomCapture]::GetWindowRect($handle, [ref]$rect) | Out-Null
    $bitmap = New-Object System.Drawing.Bitmap ($rect.Right - $rect.Left), ($rect.Bottom - $rect.Top)
    $graphics = [System.Drawing.Graphics]::FromImage($bitmap)
    $hdc = $graphics.GetHdc()
    [HlomCapture]::PrintWindow($handle, $hdc, 2) | Out-Null   # PW_RENDERFULLCONTENT
    $graphics.ReleaseHdc($hdc)
    $bitmap.Save($file, [System.Drawing.Imaging.ImageFormat]::Png)
    $graphics.Dispose(); $bitmap.Dispose()
}

# The window of the editor started by this script (other instances may be running): without a bundled JRE
# the launcher starts a separate javaw child process that owns the window.
function Get-ProcessTree([int]$id) {
    $id
    Get-CimInstance Win32_Process -Filter "ParentProcessId = $id" | ForEach-Object { Get-ProcessTree $_.ProcessId }
}

function Get-EditorWindow {
    Get-Process -Id @(Get-ProcessTree $launcher.Id) -ErrorAction SilentlyContinue |
        Where-Object { $_.MainWindowHandle -ne 0 -and $_.MainWindowTitle -like "*Hermes 2: MedBiquitous Editor*" } |
        Select-Object -First 1
}

# A fresh instance area so the window layout is the default one.
$workspace = Join-Path $env:TEMP ("hlom-shots-" + [guid]::NewGuid())
if ($StartPage) {
    $launcher = Start-Process -FilePath $exe.FullName -PassThru -ArgumentList @("-data", "`"$workspace`"")
    $window = $null
    for ($i = 0; $i -lt 120 -and -not $window; $i++) { Start-Sleep -Milliseconds 500; $window = Get-EditorWindow }
    Start-Sleep -Seconds 6
    if ($window) { Save-Window $window.MainWindowHandle (Join-Path $out "00-start.png") }
    $tree = @(Get-ProcessTree $launcher.Id)
    [array]::Reverse($tree)
    $tree | ForEach-Object { Stop-Process -Id $_ -Force -ErrorAction SilentlyContinue }
    Remove-Item -Recurse -Force $workspace -ErrorAction SilentlyContinue
    Get-ChildItem $out -Filter 00-start.png | ForEach-Object { $_.FullName }
    return
}
$launcher = Start-Process -FilePath $exe.FullName -PassThru `
    -ArgumentList @("-data", "`"$workspace`"", "`"$docPath`"", "-vmargs", "-Dhlom.screenshots=`"$out`"")

$current = Join-Path $out "current"
$done = Join-Path $out "done"
$captured = ""
$deadline = (Get-Date).AddSeconds($TimeoutSeconds)
while (-not (Test-Path $done) -and (Get-Date) -lt $deadline) {
    Start-Sleep -Milliseconds 200
    if (Test-Path $current) {
        $name = (Get-Content $current -Raw).Trim()
        if ($name -and $name -ne $captured) {
            $window = Get-EditorWindow
            if ($window) {
                Save-Window $window.MainWindowHandle (Join-Path $out "$name.png")
                $captured = $name
            }
        }
    }
}

$tree = @(Get-ProcessTree $launcher.Id)
[array]::Reverse($tree)
$tree | ForEach-Object { Stop-Process -Id $_ -Force -ErrorAction SilentlyContinue }
Remove-Item -Recurse -Force $workspace -ErrorAction SilentlyContinue
if (Test-Path $done) { Write-Output ("Editor state after viewing all pages: " + (Get-Content $done)) }
Remove-Item -Force $done, $current -ErrorAction SilentlyContinue
Get-ChildItem $out -Filter *.png | ForEach-Object { $_.FullName }
