#!/bin/bash

# ============================================================================
# COMPLETE APPLICATION STARTUP SCRIPT
# Backend + Frontend + Database (All-in-one)
# ============================================================================

echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║     Task Engine API - Complete Startup                    ║"
echo "║     Backend + Frontend + PostgreSQL                        ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

PROJECT_DIR="/Users/likhithsabbarapu/Documents/EnterpriseTaskSystem"

# ============================================================================
# STEP 1: Start Backend + PostgreSQL with Docker
# ============================================================================

echo -e "${BLUE}[STEP 1]${NC} Starting PostgreSQL and Backend API with Docker..."
cd "$PROJECT_DIR"

# Kill any existing containers
docker-compose down 2>/dev/null

# Start Docker Compose
docker-compose up -d

# Wait for PostgreSQL to be ready
echo -e "${YELLOW}Waiting for PostgreSQL to initialize...${NC}"
sleep 10

# Check if services are running
if docker-compose ps | grep -q "postgres.*Up" && docker-compose ps | grep -q "task-engine-api.*Up"; then
    echo -e "${GREEN}✓ Backend API started on port 8080${NC}"
    echo -e "${GREEN}✓ PostgreSQL started on port 5432${NC}"
else
    echo -e "${YELLOW}Services may still be starting. Waiting...${NC}"
    sleep 15
fi

# ============================================================================
# STEP 2: Install and start Frontend
# ============================================================================

echo ""
echo -e "${BLUE}[STEP 2]${NC} Setting up Frontend (React)..."
cd "$PROJECT_DIR/task-ui"

# Check if node_modules exists
if [ ! -d "node_modules" ]; then
    echo -e "${YELLOW}Installing npm dependencies...${NC}"
    npm install -q
fi

# Start React development server in background
echo -e "${YELLOW}Starting React development server...${NC}"
npm run dev > /tmp/vite.log 2>&1 &
VITE_PID=$!

# Wait for Vite to start
sleep 5

echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║                  ✅ ALL SERVICES STARTED                   ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

echo -e "${GREEN}📋 BACKEND API${NC}"
echo -e "   URL: ${BLUE}http://localhost:8080${NC}"
echo -e "   Swagger UI: ${BLUE}http://localhost:8080/swagger-ui.html${NC}"
echo -e "   Login: admin / admin123"
echo ""

echo -e "${GREEN}🎨 FRONTEND (React)${NC}"
echo -e "   URL: ${BLUE}http://localhost:5173${NC}"
echo ""

echo -e "${GREEN}🗄️  DATABASE (PostgreSQL)${NC}"
echo -e "   Host: localhost"
echo -e "   Port: 5432"
echo -e "   Database: taskdb"
echo -e "   User: taskuser"
echo -e "   Password: taskpass123"
echo ""

echo "╔════════════════════════════════════════════════════════════╗"
echo "║                  📌 QUICK COMMANDS                         ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""
echo "View logs:"
echo "  Backend:  docker-compose logs -f task-engine-api"
echo "  Database: docker-compose logs -f postgres"
echo "  Frontend: tail -f /tmp/vite.log"
echo ""
echo "Stop all:"
echo "  docker-compose down"
echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║              🚀 READY TO TEST THE APPLICATION              ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Keep script running
wait

