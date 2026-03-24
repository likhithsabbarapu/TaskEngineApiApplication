#!/bin/bash

# 🚀 Quick Start Guide for Task Engine API
# Run this file with: bash QUICK_START.sh

echo "╔════════════════════════════════════════════════════════════╗"
echo "║        Task Engine API - Quick Start Guide               ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 11 or higher."
    exit 1
fi

# Check if application is running
if ! lsof -i :8080 &> /dev/null; then
    echo "🚀 Starting Task Engine API..."
    cd /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/task-engine-api
    java -jar target/task-engine-api-0.0.1-SNAPSHOT.jar > /tmp/app.log 2>&1 &
    sleep 5
    echo "✅ Application started on port 8080"
else
    echo "✅ Application is already running on port 8080"
fi

echo ""
echo "📊 System Status:"
echo "   Backend API:  http://localhost:8080"
echo "   Swagger UI:   http://localhost:8080/swagger-ui.html"
echo "   Frontend:     http://localhost:5173"
echo "   Database:     H2 (in-memory)"
echo ""

echo "🔗 Quick API Tests:"
echo ""
echo "1️⃣  Create a task:"
echo "   curl -X POST http://localhost:8080/api/tasks \\"
echo "     -H 'Content-Type: application/json' \\"
echo "     -d '{\"title\":\"New Task\",\"status\":\"TODO\"}'"
echo ""

echo "2️⃣  Get all tasks:"
echo "   curl http://localhost:8080/api/tasks"
echo ""

echo "3️⃣  Get a specific task:"
echo "   curl http://localhost:8080/api/tasks/1"
echo ""

echo "4️⃣  Update a task:"
echo "   curl -X PUT http://localhost:8080/api/tasks/1 \\"
echo "     -H 'Content-Type: application/json' \\"
echo "     -d '{\"title\":\"Updated Task\",\"status\":\"IN_PROGRESS\"}'"
echo ""

echo "5️⃣  Delete a task:"
echo "   curl -X DELETE http://localhost:8080/api/tasks/1"
echo ""

echo "📚 Documentation:"
echo "   - View detailed docs: LOGIN_CREDENTIALS.md"
echo "   - View full summary: COMPLETION_SUMMARY.md"
echo ""

echo "🛑 To stop the application:"
echo "   lsof -i :8080 | grep -v COMMAND | awk '{print $2}' | xargs kill -9"
echo ""

echo "╔════════════════════════════════════════════════════════════╗"
echo "║              Setup Complete! Ready to use! 🎉             ║"
echo "╚════════════════════════════════════════════════════════════╝"

