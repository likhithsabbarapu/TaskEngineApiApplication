#!/bin/bash

# ============================================================================
# TASK ENGINE - ALL COMMANDS IN ONE PLACE
# ============================================================================

PROJECT_DIR="/Users/likhithsabbarapu/Documents/EnterpriseTaskSystem"

echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║        Task Engine - Command Reference                    ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""
echo "Select what you want to do:"
echo ""
echo "1. START EVERYTHING (Recommended)"
echo "2. Start backend only (Docker)"
echo "3. Start frontend only"
echo "4. Stop all services"
echo "5. View logs"
echo "6. Clean and restart"
echo "7. Open in browser"
echo "8. Run tests"
echo ""
read -p "Enter your choice (1-8): " choice

case $choice in
  1)
    echo "🚀 Starting everything..."
    bash "$PROJECT_DIR/START_ALL.sh"
    ;;
  2)
    echo "🐳 Starting Docker services..."
    cd "$PROJECT_DIR"
    docker-compose up -d
    echo "✅ Backend and database started"
    echo "   API: http://localhost:8080"
    echo "   Swagger: http://localhost:8080/swagger-ui.html"
    ;;
  3)
    echo "⚛️  Starting React frontend..."
    cd "$PROJECT_DIR/task-ui"
    npm run dev
    ;;
  4)
    echo "🛑 Stopping all services..."
    cd "$PROJECT_DIR"
    docker-compose down
    pkill -f "npm run dev"
    echo "✅ All services stopped"
    ;;
  5)
    echo "📋 Logs"
    echo ""
    echo "What logs do you want to see?"
    echo "1. Backend"
    echo "2. Database"
    echo "3. Frontend"
    read -p "Enter choice: " log_choice
    case $log_choice in
      1)
        docker-compose logs task-engine-api -f
        ;;
      2)
        docker-compose logs postgres -f
        ;;
      3)
        tail -f /tmp/vite.log
        ;;
    esac
    ;;
  6)
    echo "🔄 Full restart..."
    docker-compose down -v
    pkill -f "npm run dev"
    sleep 2
    bash "$PROJECT_DIR/START_ALL.sh"
    ;;
  7)
    echo "🌐 Opening in browser..."
    echo "Frontend: http://localhost:5173"
    echo "Backend:  http://localhost:8080/swagger-ui.html"
    open http://localhost:5173
    ;;
  8)
    echo "🧪 Running tests..."
    cd "$PROJECT_DIR/task-engine-api"
    ./mvnw test
    ;;
  *)
    echo "Invalid choice"
    ;;
esac

echo ""

