#!/bin/bash

echo "═════════════════════════════════════════════════════════"
echo "  Task Engine API - Login Application Startup"
echo "═════════════════════════════════════════════════════════"
echo ""

# Kill any existing processes
echo "🧹 Cleaning up old processes..."
pkill -9 -f "java.*task-engine" 2>/dev/null
sleep 2

# Check if JAR exists
JAR_FILE="/Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/task-engine-api/target/task-engine-api-0.0.1-SNAPSHOT.jar"

if [ ! -f "$JAR_FILE" ]; then
    echo "❌ JAR file not found! Building application..."
    cd /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/task-engine-api
    ./mvnw clean package -DskipTests -q
fi

# Start the application
echo "🚀 Starting Task Engine API..."
cd /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/task-engine-api
java -jar target/task-engine-api-0.0.1-SNAPSHOT.jar &

# Wait for startup
echo "⏳ Waiting for application to start..."
sleep 6

# Check if running
echo ""
if lsof -i :8080 > /dev/null; then
    echo "✅ Application is running!"
    echo ""
    echo "═════════════════════════════════════════════════════════"
    echo "  📍 OPEN YOUR BROWSER AND GO TO:"
    echo "═════════════════════════════════════════════════════════"
    echo ""
    echo "  🌐 http://localhost:8080/"
    echo ""
    echo "  Login Credentials:"
    echo "    Username: admin"
    echo "    Password: admin123"
    echo ""
    echo "═════════════════════════════════════════════════════════"
    echo ""
else
    echo "❌ Failed to start application!"
    echo "   Check /tmp/app.log for details"
fi

