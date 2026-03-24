# 🔐 Task Engine API - Login & Access Guide

## ✅ Current Status
- **Application**: Running on Port 8080
- **Database**: H2 In-Memory (taskdb)
- **API**: Accessible without authentication
- **Frontend**: React at `http://localhost:5173`

---

## 🎯 Quick Access

### **Task API** (No Authentication Required)
```bash
# Get all tasks
curl http://localhost:8080/api/tasks

# Get tasks with pagination
curl "http://localhost:8080/api/tasks?page=0&size=10"

# Create a task
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"My Task","status":"TODO"}'

# Update a task
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Task","status":"IN_PROGRESS"}'

# Delete a task
curl -X DELETE http://localhost:8080/api/tasks/1
```

### **Swagger UI Documentation**
- **URL**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/v3/api-docs`
- **Authentication**: None required for development

---

## 🔐 Optional Authentication (Development Credentials)

### **Default User** (if authentication is enabled in future)
- **Username**: `admin`
- **Password**: `admin123`

### **To Enable Authentication in Spring Security**
In `SecurityConfig.java`, change:
```java
.anyRequest().permitAll() // Current - no auth required
```
To:
```java
.anyRequest().authenticated() // Requires login
```

---

## 📝 Database Access

### **H2 Console** (In-Memory Database)
- **URL**: `http://localhost:8080/h2-console` (if enabled)
- **JDBC URL**: `jdbc:h2:mem:taskdb`
- **Username**: `sa`
- **Password**: (empty/blank)

### **Data Persistence**
⚠️ **Important**: H2 in-memory database is cleared on app restart. For persistent storage, configure PostgreSQL in `application.yaml`.

---

## 🛠️ Configuration Files

### **Security Configuration**
- **File**: `task-engine-api/src/main/java/com/likhith/taskengine/config/SecurityConfig.java`
- **Features**: CORS, CSRF disabled, Basic Auth ready

### **Application Configuration**
- **File**: `task-engine-api/src/main/resources/application.yaml`
- **Settings**: Database, JPA, Server port, Security user

### **Sample Updated Configuration**
```yaml
spring:
  security:
    user:
      name: admin
      password: admin123
  datasource:
    url: jdbc:h2:mem:taskdb
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: create-drop
```

---

## 🚀 Available Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|----------------|
| GET | `/api/tasks` | Get all tasks with pagination | ❌ No |
| GET | `/api/tasks/{id}` | Get task by ID | ❌ No |
| POST | `/api/tasks` | Create new task | ❌ No |
| PUT | `/api/tasks/{id}` | Update existing task | ❌ No |
| DELETE | `/api/tasks/{id}` | Delete task | ❌ No |
| GET | `/swagger-ui.html` | API documentation | ❌ No |
| GET | `/v3/api-docs` | OpenAPI specification | ❌ No |

---

## 📋 Task Status Values
- `TODO` - Task not started
- `IN_PROGRESS` - Task is being worked on
- `DONE` - Task completed

---

## 🔗 Frontend Integration

### **React Frontend URL**
- **URL**: `http://localhost:5173`
- **API Base**: `http://localhost:8080`
- **Axios Configuration**: `src/api/axios.js`

### **CORS Settings**
Allowed origins for API requests:
- `http://localhost:5173`
- `http://localhost:3000`

---

## 🆘 Troubleshooting

### **Port 8080 Already in Use**
```bash
# Kill the process using port 8080
lsof -i :8080 | grep -v COMMAND | awk '{print $2}' | xargs kill -9
```

### **Check Application Logs**
```bash
# View last 50 lines of logs
tail -n 50 /tmp/app.log

# Search for errors
grep -i "error\|exception" /tmp/app.log
```

### **Rebuild and Restart**
```bash
cd task-engine-api
./mvnw clean install -DskipTests
java -jar target/task-engine-api-0.0.1-SNAPSHOT.jar
```

---

## 📚 Additional Resources

- **Spring Boot**: https://spring.io/projects/spring-boot
- **Spring Security**: https://spring.io/projects/spring-security
- **H2 Database**: https://www.h2database.com/
- **Swagger/OpenAPI**: https://swagger.io/tools/swagger-ui/

