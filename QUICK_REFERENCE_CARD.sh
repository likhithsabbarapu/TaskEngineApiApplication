#!/bin/bash

# ============================================================================
# TASK ENGINE API - QUICK REFERENCE CARD
# ============================================================================
# Print this for easy reference

cat << 'EOF'

╔════════════════════════════════════════════════════════════════════════════╗
║                   TASK ENGINE - QUICK REFERENCE                           ║
╚════════════════════════════════════════════════════════════════════════════╝

📍 LOCATION
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
/Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/

🚀 START APPLICATION
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
bash START_ALL.sh                    (Everything in one command)

OR manually:
  docker-compose up -d               (Backend + Database in Terminal 1)
  cd task-ui && npm run dev          (Frontend in Terminal 2)

📍 ACCESS URLS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Frontend:    http://localhost:5173
API:         http://localhost:8080/api
Swagger:     http://localhost:8080/swagger-ui.html

🔐 CREDENTIALS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
API Login:           admin / admin123
Database:            taskuser / taskpass123
Database Host:       localhost:5432

🛑 STOP SERVICES
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
docker-compose down                  (Stop all Docker services)
Ctrl+C in Terminal 2                 (Stop frontend)

📋 DOCKER COMMANDS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
docker-compose ps                    (See status)
docker-compose logs -f               (View logs)
docker-compose restart               (Restart all)
docker-compose down -v               (Delete everything)

🔧 FRONTEND COMMANDS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
cd task-ui
npm install                          (Install dependencies)
npm run dev                          (Start dev server)
npm run build                        (Build for production)

📚 API TESTING
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
curl http://localhost:8080/api/tasks
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Task","status":"TODO"}'

🔍 TROUBLESHOOTING
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Cannot connect:      docker-compose ps (check status)
Port in use:         docker-compose down (stop first)
npm error:           rm -rf node_modules && npm install
Database failed:     docker-compose restart postgres

📁 IMPORTANT FILES
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
START_ALL.sh                         (Main startup script)
COMPLETE_SETUP_GUIDE.md              (Full documentation)
docker-compose.yml                   (Docker configuration)
task-engine-api/                     (Backend source)
task-ui/                             (Frontend source)

📊 STATUS INDICATORS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
🟢 Connected                         Backend is online
🔴 Offline                           Backend is down
✅ Connected                         All systems ready
❌ Error                             Check logs

💡 TIPS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
• Use START_ALL.sh for easiest startup
• Frontend refreshes tasks every 5 seconds
• Check Swagger UI for API documentation
• Use browser DevTools for debugging
• Data persists across restarts

═════════════════════════════════════════════════════════════════════════════

Ready to start? Run:
  bash START_ALL.sh

Questions? Check:
  COMPLETE_SETUP_GUIDE.md
  README_FINAL.md

═════════════════════════════════════════════════════════════════════════════
EOF

# Print this file for reference
echo ""
echo "Saved to: /tmp/QUICK_REFERENCE.txt"
echo ""

