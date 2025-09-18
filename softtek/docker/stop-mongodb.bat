@echo off
echo ================================
echo  Parando MongoDB
echo ================================

cd /d "%~dp0"

echo Parando containers do MongoDB...
docker-compose down

if %errorlevel% equ 0 (
    echo.
    echo MongoDB parado com sucesso! ✓
    echo.
    echo Para iniciar novamente, execute: start-mongodb.bat
) else (
    echo.
    echo ERRO: Falha ao parar o MongoDB
    echo Verifique os containers com: docker ps
)

pause
