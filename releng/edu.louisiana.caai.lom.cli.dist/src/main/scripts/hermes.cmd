@echo off
rem Copyright (c) 2026 University of Louisiana at Lafayette, CAAI.
rem Runs the Hermes 2 command line tool ("hermes help" lists the commands).
rem Uses the Java runtime of an enclosing Hermes 2 installation, else JAVA_HOME, else java on the PATH (Java 25+).
setlocal
set "dir=%~dp0"
if exist "%dir%..\jre\bin\java.exe" (
  set "java=%dir%..\jre\bin\java.exe"
) else if defined JAVA_HOME (
  set "java=%JAVA_HOME%\bin\java.exe"
) else (
  set "java=java"
)
"%java%" -XX:+UseSerialGC -XX:TieredStopAtLevel=1 -Xshare:auto -jar "%dir%hermes.jar" %*
exit /b %ERRORLEVEL%
