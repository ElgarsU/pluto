#!/bin/bash
# Build the backend Spring Boot application
echo "Building backend..."
cd backend
./mvnw clean package
cd ..

# Build the frontend Nuxt application
echo "Building frontend..."
cd frontend
npm ci
npm run build
cd ..

# Bbuild and start Docker containers
echo "Starting Docker containers..."
docker-compose up --build
