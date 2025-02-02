@echo off
REM Build the backend Spring Boot application
echo Building backend...
cd backend
call mvnw.cmd clean package
cd ..

REM Build the frontend Nuxt application
echo Building frontend...
cd frontend
npm ci
npm run build
cd ..

REM Build and start Docker containers
echo Starting Docker containers...
docker-compose up --build

pause
