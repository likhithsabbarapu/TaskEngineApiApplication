# 🔧 EMERGENCY RECOVERY GUIDE

## ❌ PROBLEM: "Unable to open - unexpected error"

This means the application is **not running** on port 8080.

---

## ✅ QUICK FIX (Copy & Paste)

### Open Terminal and run:

```bash
bash /tmp/emergency_recovery.sh
```

Then open your browser to:
```
http://localhost:8080/
```

**Done!** You should see the login page.

---

## 🔍 DETAILED TROUBLESHOOTING

### Step 1: Check if app is running
```bash
lsof -i :8080
```

**Expected output**: Shows java process listening on port 8080

**If empty**: App is not running → proceed to Step 2

---

### Step 2: Kill old processes
```bash
pkill -9 java
```

Wait 2 seconds, then check again:
```bash
lsof -i :8080
```

Should now be empty.

---

### Step 3: Build the application

Navigate to the app directory:
```bash
cd /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/task-engine-api
```

Rebuild:
```bash
./mvnw clean package -DskipTests
```

This will take 30-60 seconds. Wait for it to complete.

---

### Step 4: Start the application

```bash
java -jar target/task-engine-api-0.0.1-SNAPSHOT.jar
```

You should see:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___  '_  '_  '_ \/ _`  \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  ____ .___ __ _\__,  / / / /
 =========_==============___/=/_/_/_/

 :: Spring Boot ::                (v4.0.4)

2026-03-23T... INFO ... Starting TaskEngineApiApplication
...
2026-03-23T... INFO ... Tomcat started on port 8080
```

---

### Step 5: Open browser

Once you see "Tomcat started on port 8080", open:

```
http://localhost:8080/
```

You should see the login page! ✅

---

## 📝 WHAT TO DO AT LOGIN PAGE

1. **Username field** → Already filled with `admin`
2. **Password field** → Already filled with `admin123`
3. **Click "Sign In"** button
4. **You'll be redirected** to the API documentation page

---

## ❓ WHAT IF IT STILL DOESN'T WORK?

### Check port availability
```bash
lsof -i :8080
```

If something else is using port 8080, kill it:
```bash
# Find what's using the port
lsof -i :8080

# Kill it (replace PID with actual process ID)
kill -9 <PID>
```

---

### Check JAR file exists
```bash
ls -lh /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/task-engine-api/target/task-engine-api*.jar
```

Should show the JAR file (around 67MB).

If not, rebuild:
```bash
cd /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/task-engine-api
./mvnw clean package -DskipTests
```

---

### Check for errors in logs
```bash
tail -100 /tmp/app_startup.log
```

Look for ERROR or Exception messages.

---

## 🎯 COMPLETE STEP-BY-STEP SOLUTION

Copy and paste each command one at a time:

```bash
# 1. Go to app directory
cd /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/task-engine-api

# 2. Kill old processes
pkill -9 java

# 3. Wait
sleep 2

# 4. Clean rebuild (takes 30-60 seconds)
./mvnw clean package -DskipTests

# 5. Start application
java -jar target/task-engine-api-0.0.1-SNAPSHOT.jar
```

Wait for "Tomcat started on port 8080", then open:
```
http://localhost:8080/
```

---

## 🚀 ALTERNATIVE: Use Startup Scripts

### Option 1: Start with Login
```bash
bash /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/START_WITH_LOGIN.sh
```

### Option 2: Original Quick Start
```bash
bash /Users/likhithsabbarapu/Documents/EnterpriseTaskSystem/QUICK_START_GUIDE.sh
```

### Option 3: Emergency Recovery
```bash
bash /tmp/emergency_recovery.sh
```

---

## 📋 CREDENTIALS (at login page)

| Field | Value |
|-------|-------|
| Username | admin |
| Password | admin123 |

**Both fields are pre-filled** - just click "Sign In"!

---

## ✨ WHAT YOU'LL SEE

### 1. Login Page (http://localhost:8080/)
```
Beautiful purple gradient background
White form box with:
  - Username field (pre-filled: admin)
  - Password field (pre-filled: admin123)
  - "Sign In" button
  - Links to API documentation
```

### 2. After Login
```
Redirected to: http://localhost:8080/swagger-ui.html
Shows: Interactive API documentation
```

---

## 🆘 IF NOTHING WORKS

Check the application is actually running:
```bash
# Should show java process
lsof -i :8080

# Should return SUCCESS or show running processes
ps aux | grep java

# Try accessing API directly
curl http://localhost:8080/api/tasks
```

If curl returns data → App is working, use different browser
If curl fails → App is not running, rebuild and start again

---

## 📞 FINAL CHECKLIST

- ✅ Killed old Java processes
- ✅ Navigated to task-engine-api directory
- ✅ Ran `./mvnw clean package -DskipTests`
- ✅ Started with `java -jar target/task-engine-api-0.0.1-SNAPSHOT.jar`
- ✅ Waited for "Tomcat started on port 8080"
- ✅ Opened http://localhost:8080/ in browser
- ✅ Saw login page
- ✅ Clicked Sign In
- ✅ Redirected to Swagger UI

**If all checked → You're good!** ✅

---

**Status**: Recovery guide ready
**Last Updated**: March 23, 2026

