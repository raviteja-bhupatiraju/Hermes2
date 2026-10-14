@echo off
rem Builds Hermes 2 (Windows). Tests are skipped; run test.cmd for them.
rem
rem   build               build the application and make the release files in dist\ (Windows setup, Linux,
rem                       macOS, CLI)
rem   build -app          build the application only (what run.cmd starts), no release files
rem   build -clean        remove the previous build output first
rem   build -offline      don't contact the network (uses the local Maven repository)
rem
rem Options can be combined (build -clean -offline); any other arguments are passed to Maven (quote those with "="
rem or ",").
rem The modules are built in parallel (one thread per core). The console shows the progress; Maven's full output
rem is written to build.log. Started from Explorer (double-click), the window stays open until a key is pressed.
rem Needs JDK 25 or later (JAVA_HOME, or the first Java on the PATH); the release build makes the installer only
rem when Inno Setup is installed.

setlocal enabledelayedexpansion
set "ROOT=%~dp0"
set "LOG=%ROOT%build.log"
set "GOALS=verify"
set "MAVEN=-B -ntp -T 1C -DskipTests"
set RELEASE=1

rem Double-clicked: cmd.exe was started for this script alone ("cmd /c ""...\build.cmd" ").
set PAUSE=0
echo !CMDCMDLINE! | findstr /i /c:"%~nx0" >nul && set PAUSE=1

:parse
if "%~1"=="" goto parsed
if /i "%~1"=="-app" (
    set RELEASE=0
) else if /i "%~1"=="-release" (
    set RELEASE=1
) else if /i "%~1"=="-clean" (
    set "GOALS=clean verify"
) else if /i "%~1"=="-offline" (
    set "MAVEN=%MAVEN% -o"
) else (
    set "MAVEN=%MAVEN% %1"
)
shift
goto parse
:parsed
if %RELEASE%==1 set "MAVEN=%MAVEN% -Prelease"

rem A running Hermes 2 locks the files of the build it was started from.
tasklist /fi "imagename eq Hermes2.exe" 2>nul | find /i "Hermes2.exe" >nul && (
    echo Hermes 2 is running; close it before building.
    goto failed
)

echo Building Hermes 2...
call "%ROOT%mvnw.cmd" -f "%ROOT%pom.xml" %MAVEN% %GOALS% 2>&1 ^
    | powershell -NoProfile -NonInteractive -ExecutionPolicy Bypass -File "%ROOT%tools\build-progress.ps1" -Log "%LOG%" ^
    || goto failed
echo Done.
if %RELEASE%==1 echo The release files are in %ROOT%dist
if %PAUSE%==1 pause
exit /b 0

:failed
echo The build failed.
if %PAUSE%==1 pause
exit /b 1
