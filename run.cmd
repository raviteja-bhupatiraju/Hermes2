@echo off
rem Starts the locally built Hermes 2 (Windows).
rem
rem   run                 start the last build (builds first if there is none)
rem   run -build          rebuild (skipping tests), then start
rem   run -fresh          start with an empty workspace (default layout, no projects or preferences)
rem   run -build -fresh   both; any other arguments are passed to Hermes2.exe (e.g. a document to open)
rem
rem Started from Explorer (double-click), the script continues without a console window: a build writes its
rem output to run-build.log, and problems are shown in a message box. From a command prompt it runs in the
rem console as usual.
rem
rem Hermes 2 needs Java 25 or later: the one in JAVA_HOME if it is set, else the first on the PATH.
rem The workspace is the "workspace" folder next to this script (ignored by git).

setlocal enabledelayedexpansion
set "ROOT=%~dp0"
set "EXE=%ROOT%releng\edu.louisiana.caai.lom.product\target\products\edu.louisiana.caai.lom.product\win32\win32\x86_64\Hermes2\Hermes2.exe"
set "WORKSPACE=%ROOT%workspace"
set "LOG=%ROOT%run-build.log"
set BUILD=0
set FRESH=0
set HIDDEN=0
set "ARGS="

rem Double-clicked: cmd.exe was started for this script alone ("cmd /c ""...\run.cmd" "). Run it again without a
rem window and close this one.
echo !CMDCMDLINE! | findstr /i /c:"%~nx0" >nul && if /i not "%~1"=="-hidden" (
    powershell -NoProfile -NonInteractive -WindowStyle Hidden -Command ^
        "Start-Process -WindowStyle Hidden -FilePath $env:ComSpec -ArgumentList '/c', ('\"\"' + '%~f0' + '\" -hidden %*\"')"
    exit /b
)

:parse
if "%~1"=="" goto parsed
if /i "%~1"=="-build" (
    set BUILD=1
) else if /i "%~1"=="-fresh" (
    set FRESH=1
) else if /i "%~1"=="-hidden" (
    set HIDDEN=1
) else (
    set "ARGS=!ARGS! "%~1""
)
shift
goto parse
:parsed

if not exist "%EXE%" set BUILD=1

if %BUILD%==1 (
    tasklist /fi "imagename eq Hermes2.exe" 2>nul | find /i "Hermes2.exe" >nul && (
        call :report "Hermes 2 is running; close it before building."
        exit /b 1
    )
    if %HIDDEN%==1 (
        call "%ROOT%mvnw.cmd" -q -DskipTests verify > "%LOG%" 2>&1 || (
            call :report "The build failed. See %LOG%"
            exit /b 1
        )
    ) else (
        echo Building Hermes 2 ^(tests skipped^)...
        call "%ROOT%mvnw.cmd" -q -DskipTests verify || (
            echo The build failed.
            exit /b 1
        )
    )
)

if %FRESH%==1 if exist "%WORKSPACE%" rmdir /s /q "%WORKSPACE%"

set "VM="
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\javaw.exe" set VM=-vm "%JAVA_HOME%\bin\javaw.exe"

start "" "%EXE%" -data "%WORKSPACE%" %VM% %ARGS%
exit /b 0

rem Shows a message in the console, or in a message box when running without one.
:report
if %HIDDEN%==1 (
    powershell -NoProfile -NonInteractive -WindowStyle Hidden -Command ^
        "Add-Type -AssemblyName PresentationFramework; [void][System.Windows.MessageBox]::Show('%~1', 'Hermes 2')"
) else (
    echo %~1
)
exit /b 0
