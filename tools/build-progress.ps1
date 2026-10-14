# Condenses the Maven output of build.cmd into progress lines; the full output goes to the log file.
# Reads the output from the pipeline and exits with 0 only when Maven reported BUILD SUCCESS.
param([string]$Log = "build.log")

$clock = [Diagnostics.Stopwatch]::StartNew()
$writer = [IO.StreamWriter]::new($Log, $false, [Text.UTF8Encoding]::new($false))
$success = $false
$warnings = 0
$summary = $false
$failures = [Collections.Generic.List[string]]::new()
$shown = [Collections.Generic.HashSet[string]]::new()
$boilerplate = '^(To see the full stack trace|Re-run Maven|For more information about the errors|\[Help \d+\]|After correcting the problems|  mvn <args> -rf)'

function Show([string]$text, [string]$color = 'Gray') {
    $time = '{0}:{1:00}' -f [int][Math]::Floor($clock.Elapsed.TotalMinutes), $clock.Elapsed.Seconds
    Write-Host ('{0,6}  ' -f $time) -NoNewline -ForegroundColor DarkGray
    Write-Host $text -ForegroundColor $color
}

function Short([string]$module) { $module -replace '^edu\.louisiana\.caai\.', '' }

while ($null -ne ($line = [Console]::In.ReadLine())) {
    $writer.WriteLine($line)
    $text = $line -replace '^\[(INFO|WARNING|ERROR)\] ?', ''
    switch -regex ($line) {
        '^\[INFO\] Using the MultiThreadedBuilder implementation with a thread count of (\d+)' {
            Show "Building in parallel ($($Matches[1]) threads)"; break
        }
        '^\[INFO\] Building (.+?) \S+\s+\[\s*(\d+)/(\d+)\]' {
            Show ('[{0,2}/{1}] {2}' -f $Matches[2], $Matches[3], (Short $Matches[1])) White; break
        }
        '^\[INFO\] Resolving target definition' { Show 'Resolving the target platform'; break }
        '^\[INFO\] Installing product \S+ for environment (\S+)' { Show "  product for $($Matches[1])"; break }
        '^\[INFO\] Building (zip|tar): .*\\products\\(.+)$' { Show "  archive $($Matches[2])"; break }
        '^\[INFO\] --- antrun:\S+ \(release-archives\)' { Show '  release installers and archives'; break }
        '^\[(INFO|WARNING)\]\s+\[echo\] (.+)$' { Show "  $($Matches[2])"; break }
        '^\[INFO\] Reactor Summary' { $summary = $true; break }
        '^\[INFO\] (.+?) \.+ (FAILURE|SKIPPED)' { if ($summary) { $failures.Add("$(Short $Matches[1]): $($Matches[2])") }; break }
        '^\[INFO\] BUILD SUCCESS' { $success = $true; break }
        '^\[ERROR\]' {
            # After the build Maven repeats the errors, with advice on rerunning it and help links.
            $text = $text -replace ' -> \[Help \d+\]$', ''
            if ($text.Trim() -and $text -notmatch $boilerplate -and $shown.Add($text)) { Show $text Red }
            break
        }
        '^\[WARNING\]' { if ($text.Trim()) { $warnings++ }; break }
    }
}
$writer.Close()

foreach ($failure in $failures) { Show $failure Red }
if ($warnings) { Show "$warnings warning line(s); see $Log" Yellow }
Show "Full output: $Log" DarkGray
exit $(if ($success) { 0 } else { 1 })
