::echo on

echo off

set BASE_DIR=%~dp0
set DRIVER=%BASE_DIR:~0,2%

echo "BASE_DIR="%BASE_DIR%
echo "DRIVER="%DRIVER%

%DRIVER%
cd %BASE_DIR%

:: set path envionment variable
set path=%path%;"F:\ProgramFiles\android\Android Studio\gradle\gradle-2.14.1\bin"

gradle -v
gradle tasks
gradle clean
gradle build
gradle compileReleaseSource

pause