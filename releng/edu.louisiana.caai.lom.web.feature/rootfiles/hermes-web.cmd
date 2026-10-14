@echo off
rem Starts Hermes 2 Web at http://localhost:8080/hermes (another port: set HERMES_WEB_PORT=9090).
rem Needs Java 25 or later on the PATH (or JAVA_HOME).
setlocal
cd /d "%~dp0"
if "%HERMES_WEB_PORT%"=="" set HERMES_WEB_PORT=8080
set JAVA=java
if not "%JAVA_HOME%"=="" set JAVA=%JAVA_HOME%\bin\java
for %%f in (plugins\org.eclipse.equinox.launcher_*.jar) do set LAUNCHER=%%f
"%JAVA%" -Dorg.osgi.service.http.port=%HERMES_WEB_PORT% -Dorg.eclipse.rap.http.jetty.http.port=%HERMES_WEB_PORT% -jar "%LAUNCHER%" -consoleLog %*
