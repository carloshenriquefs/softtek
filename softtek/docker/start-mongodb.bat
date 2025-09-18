@echo off
echo ================================
echo  MongoDB para Saude Mental API
echo ================================

echo.
echo Verificando se o Docker esta instalado...
docker --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERRO: Docker nao encontrado!
    echo Por favor, instale o Docker Desktop primeiro:
    echo https://www.docker.com/products/docker-desktop/
    pause
    exit /b 1
)

echo Docker encontrado! ✓
echo.

cd /d "%~dp0"

echo Iniciando MongoDB...
docker-compose up -d

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo  MongoDB iniciado com sucesso! ✓
    echo ========================================
    echo.
    echo 📊 MongoDB rodando em: localhost:27017
    echo 🌐 Mongo Express: http://localhost:8081
    echo.
    echo Usuario: admin
    echo Senha: password123
    echo Database: saude_mental
    echo.
    echo Para parar o MongoDB, execute: stop-mongodb.bat
    echo ========================================
) else (
    echo.
    echo ERRO: Falha ao iniciar o MongoDB
    echo Verifique os logs com: docker-compose logs
)

pause
