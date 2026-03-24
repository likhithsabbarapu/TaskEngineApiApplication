# 🚀 COMPLETE SETUP GUIDE - Backend + Frontend + Database

## ⚡ FASTEST WAY: One Command Startup

Open Terminal and run:

```bash
bash /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/START_ALL.sh
```

This will:
✅ Start PostgreSQL database (Docker)
✅ Build and start Spring Boot API
✅ Install and start React frontend
✅ Show you all URLs

Then:
- 🌐 **Frontend**: http://localhost:5173
- 🔌 **Backend API**: http://localhost:8080
- 📊 **Swagger UI**: http://localhost:8080/swagger-ui.html

---

## 🔧 MANUAL SETUP (Step by Step)

### STEP 1: Start Backend + Database (Docker)

```bash
cd /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem
docker-compose up -d
```

Wait 15-20 seconds for PostgreSQL to start.

Check status:
```bash
docker-compose ps
```

Should show both services as "Up":
```
postgres        Up
task-engine-api Up
```

### STEP 2: Start Frontend (React)

Open a **NEW Terminal window** and run:

```bash
cd /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/task-ui
npm install  # Only first time
npm run dev
```

You'll see:
```
  VITE v4.x.x
  ➜  Local:   http://localhost:5173/
```

---

## 📍 ACCESS YOUR APPLICATION

### Frontend (React UI)
```
http://localhost:5173
```
Features:
- ✅ Create tasks
- ✅ View all tasks
- ✅ Update task status (TODO → IN_PROGRESS → DONE)
- ✅ Delete tasks
- ✅ Live connection status indicator

### Backend API (Swagger)
```
http://localhost:8080/swagger-ui.html
```
Features:
- ✅ Interactive API documentation
- ✅ Test endpoints directly
- ✅ View request/response examples

### Direct API Testing
```bash
# Get all tasks
curl http://localhost:8080/api/tasks

# Create a task
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"My Task","status":"TODO"}'

# Update task status
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"id":1,"title":"My Task","status":"IN_PROGRESS","description":""}'

# Delete task
curl -X DELETE http://localhost:8080/api/tasks/1
```

---

## 🔐 CREDENTIALS & DETAILS

### Database (PostgreSQL)
```
Host:     localhost
Port:     5432
Database: taskdb
Username: taskuser
Password: taskpass123
```

### API Authentication
```
Username: admin
Password: admin123
```

### Frontend Configuration
- Connects to: `http://localhost:8080/api`
- Auto-reconnects if backend restarts
- Shows connection status in UI
- 5-second auto-refresh of tasks

---

## 🛑 STOPPING EVERYTHING

### Stop Only Docker (Keep Terminal/Frontend Running)
```bash
docker-compose down
```

### Stop Frontend
In the terminal running `npm run dev`, press:
```
Ctrl+C
```

### Full Cleanup (Delete All Data)
```bash
docker-compose down -v
```

### Kill All and Start Fresh
```bash
# Stop everything
docker-compose down
pkill -f "npm run dev"
pkill -f vite

# Start fresh
bash START_ALL.sh
```

---

## ✅ FEATURES INCLUDED

### Backend (Spring Boot)
- ✅ RESTful API
- ✅ PostgreSQL database
- ✅ Swagger documentation
- ✅ Authentication ready
- ✅ Error handling
- ✅ CORS configured

### Frontend (React)
- ✅ Task CRUD operations
- ✅ Real-time connection status
- ✅ Auto-refresh tasks
- ✅ Status filtering
- ✅ Responsive design
- ✅ Error notifications

### Database (PostgreSQL)
- ✅ Production-grade
- ✅ Persistent data
- ✅ Automatic backups
- ✅ Connection pooling

---

## 🔍 TROUBLESHOOTING

### "Cannot connect to backend"
**Problem**: Frontend shows 🔴 offline

**Solution**:
```bash
# Check if Docker is running
docker-compose ps

# Restart if needed
docker-compose down
docker-compose up -d
sleep 10
```

### "Port 5173 already in use"
**Problem**: Vite can't start frontend

**Solution**:
```bash
# Kill the process using port 5173
lsof -i :5173
kill -9 <PID>

# Restart
npm run dev
```

### "Port 8080 already in use"
**Problem**: Docker can't start API

**Solution**:
```bash
# Kill and restart Docker
docker-compose down
sleep 2
docker-compose up -d
```

### "Database connection failed"
**Problem**: PostgreSQL not responding

**Solution**:
```bash
# Check logs
docker-compose logs postgres

# Restart database
docker-compose restart postgres
sleep 5
```

### "npm install fails"
**Problem**: Missing dependencies

**Solution**:
```bash
cd /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/task-ui
rm -rf node_modules package-lock.json
npm install
npm run dev
```

---

## 📊 QUICK COMMANDS

```bash
# View all commands
bash START_ALL.sh

# Docker commands
docker-compose up -d              # Start all
docker-compose down               # Stop all
docker-compose ps                 # View status
docker-compose logs -f            # View logs
docker-compose restart            # Restart all
docker-compose down -v            # Stop and delete data

# Frontend commands
cd task-ui
npm install                       # Install dependencies
npm run dev                       # Start dev server
npm run build                     # Build for production
npm run preview                   # Preview build

# API commands
curl http://localhost:8080/api/tasks                    # Get all
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Task","status":"TODO"}'                 # Create
```

---

## 🎯 EXPECTED OUTPUT

When you run `START_ALL.sh`:

```
╔════════════════════════════════════════════════════════════╗
║     Task Engine API - Complete Startup                    ║
║     Backend + Frontend + PostgreSQL                        ║
╚════════════════════════════════════════════════════════════╝

[STEP 1] Starting PostgreSQL and Backend API with Docker...
Waiting for PostgreSQL to initialize...
✓ Backend API started on port 8080
✓ PostgreSQL started on port 5432

[STEP 2] Setting up Frontend (React)...
Installing npm dependencies...
Starting React development server...

╔════════════════════════════════════════════════════════════╗
║                  ✅ ALL SERVICES STARTED                   ║
╚════════════════════════════════════════════════════════════╝

📋 BACKEND API
   URL: http://localhost:8080
   Swagger UI: http://localhost:8080/swagger-ui.html
   Login: admin / admin123

🎨 FRONTEND (React)
   URL: http://localhost:5173

🗄️  DATABASE (PostgreSQL)
   Host: localhost
   Port: 5432
   Database: taskdb
   User: taskuser
   Password: taskpass123
```

---

## ✨ ARCHITECTURE

```
┌─────────────────────────────────────────────────────────────┐
│                    COMPLETE APPLICATION                     │
└─────────────────────────────────────────────────────────────┘

┌──────────────────────┐            ┌──────────────────────────┐
│   React Frontend     │            │  Spring Boot Backend API │
│  (localhost:5173)    │◄──HTTP────►│    (localhost:8080)      │
│                      │   /api     │                          │
│  • Tasks UI          │            │  • Task Controller       │
│  • Status Display    │            │  • Task Service          │
│  • Error Handling    │            │  • Task Repository       │
│  • Auto-refresh      │            │  • Swagger Docs          │
└──────────────────────┘            └──────────────┬───────────┘
                                                   │
                                    ┌──────────────▼────────────┐
                                    │  PostgreSQL Database      │
                                    │  (localhost:5432)         │
                                    │                           │
                                    │  • Tasks Table            │
                                    │  • Persistent Storage     │
                                    │  • Connection Pool        │
                                    └──────────────────────────┘
```

---

## 🚀 NEXT STEPS

1. **Run**: `bash START_ALL.sh`
2. **Wait**: 30-60 seconds for startup
3. **Visit**: http://localhost:5173
4. **Add Tasks**: Create your first task!
5. **Explore**: Check Swagger UI at http://localhost:8080/swagger-ui.html

---

**Everything is configured and ready to use! 🎉**

Last Updated: March 23, 2026

