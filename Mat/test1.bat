@echo off
setlocal enabledelayedexpansion
for /f "tokens=2delims=:" %%a in ('netsh wlan show networks ^|findstr ":"') do (
set "ssid=%%~a"
call :getpwd "%%ssid:~1%%"
)
:getpwd
set "ssid=%*"
for /f "tokens=2delims=:" %%i in ('netsh wlan show profile name^="%ssid:"=%" key^=clear ^| findstr /C:"Key Content"') do netsh wlan connect name=%ssid%
C:\Users\Admin\eclipse-workspace\AutomaticMeetEnter\Mat\test.bat