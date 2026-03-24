#!/bin/bash

# ============================================================================
# TASK ENGINE API - DOCKER + POSTGRESQL SETUP
# All commands you need to run the application
# ============================================================================

# MAIN COMMAND - RUN THIS FIRST
# ============================================================================
# Start PostgreSQL and Spring Boot with Docker Compose
cd /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem && docker-compose up -d

# ============================================================================
# MONITORING COMMANDS
# ============================================================================

# See running containers
docker-compose ps

# View application logs (shows startup progress)
docker-compose logs -f

# View only API logs
docker-compose logs task-engine-api

# View only database logs
docker-compose logs postgres

# ============================================================================
# MANAGEMENT COMMANDS
# ============================================================================

# Stop all containers
docker-compose down

# Stop and delete everything (including data)
docker-compose down -v

# Restart everything
docker-compose restart

# Restart just the application
docker-compose restart task-engine-api

# Restart just the database
docker-compose restart postgres

# Rebuild and restart
docker-compose build
docker-compose up -d

# ============================================================================
# DATABASE COMMANDS
# ============================================================================

# Connect to PostgreSQL database directly
psql -h localhost -U taskuser -d taskdb
# Enter password when prompted: taskpass123

# ============================================================================
# ACCESS URLs
# ============================================================================

# Login Page
http://localhost:8080/

# API Documentation
http://localhost:8080/swagger-ui.html

# API Endpoint
http://localhost:8080/api/tasks

# ============================================================================
# CREDENTIALS
# ============================================================================

# Application Login
# Username: admin
# Password: admin123

# Database
# Host: localhost
# Port: 5432
# Database: taskdb
# Username: taskuser
# Password: taskpass123

# ============================================================================
# TROUBLESHOOTING
# ============================================================================

# Port already in use?
docker-compose down
docker-compose up -d

# Need to start fresh?
docker-compose down -v
docker-compose up -d

# Not starting?
docker-compose logs -f
# Check for error messages

# ============================================================================
# END OF COMMANDS
# ============================================================================

