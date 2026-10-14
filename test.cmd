@echo off
rem Runs the tests of Hermes 2 (Windows).
rem
rem   test                    the unit tests (fast, no windows)
rem   test -ui                the unit tests and the user interface tests (several minutes; the test window
rem                           opens off-screen)
rem   test -show              like -ui, but with the test window on screen (to watch the tests)
rem   test -only "A,B"        only the given test classes (unit or user interface tests), e.g. test -only FormLayoutTest
rem   test -offline           don't contact the network (uses the local Maven repository)
rem
rem Options can be combined; any other arguments are passed to Maven (quote those with "=" or ","). The installers
rem aren't built (build.cmd).
rem Needs JDK 25 or later (JAVA_HOME, or the first Java on the PATH).

setlocal
set "ROOT=%~dp0"
set "UI=0"
set "MAVEN=-q"
rem The product is not needed for the tests (and can't be rebuilt while Hermes 2 runs from it).
set "SKIP=-releng/edu.louisiana.caai.lom.product"

:parse
if "%~1"=="" goto parsed
if /i "%~1"=="-ui" (
    set "UI=1"
) else if /i "%~1"=="-show" (
    set "UI=1"
    set "MAVEN=%MAVEN% -Dhermes.offscreen=false"
) else if /i "%~1"=="-only" (
    set "UI=1"
    set "MAVEN=%MAVEN% -Dtest=%~2 -DfailIfNoTests=false -Dsurefire.failIfNoSpecifiedTests=false"
    shift
) else if /i "%~1"=="-offline" (
    set "MAVEN=%MAVEN% -o"
) else (
    set "MAVEN=%MAVEN% %1"
)
shift
goto parse
:parsed

if %UI%==0 set "SKIP=%SKIP%,-tests/edu.louisiana.caai.lom.ui.tests"

if %UI%==1 (echo Running the unit and user interface tests...) else (echo Running the unit tests...)
call "%ROOT%mvnw.cmd" -f "%ROOT%pom.xml" %MAVEN% -pl "%SKIP%" verify || (
    echo Tests failed; the failures are listed above ^(details in tests\*\target\surefire-reports^).
    exit /b 1
)
echo All tests passed.
exit /b 0
