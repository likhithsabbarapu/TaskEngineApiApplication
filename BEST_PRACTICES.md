# 🎓 Best Practices & Configuration Guide

## Part 1: Security Best Practices

### Current Development Setup
```yaml
# ✅ GOOD for development
spring:
  security:
    user:
      name: admin
      password: admin123
```

### Production Setup (Recommended)
```yaml
# 🔒 BETTER for production
spring:
  security:
    user:
      name: ${AUTH_USERNAME}
      password: ${AUTH_PASSWORD}
  datasource:
    url: jdbc:postgresql://prod-db-host:5432/taskdb
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

### Implementation Steps
1. Move sensitive data to environment variables
2. Use Spring Cloud Config for centralized management
3. Implement JWT token-based authentication
4. Add password hashing (BCryptPasswordEncoder already configured)
5. Enable HTTPS/TLS for all communications

---

## Part 2: Performance Optimization

### Database Optimization
```java
// Add indexes in future migration
@Entity
@Table(name = "task", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class Task {
    // ... fields
}
```

### Caching Strategy
```java
@Cacheable("tasks")
public Page<Task> getAllTasks(Pageable pageable) {
    return taskRepository.findAll(pageable);
}
```

### Query Optimization
```java
// Current: Generic findAll()
// Better: Specific queries
@Query("SELECT NEW com.likhith.taskengine.dto.TaskResponseDTO(...) " +
       "FROM Task t WHERE t.status = :status")
Page<TaskResponseDTO> findByStatus(String status, Pageable pageable);
```

---

## Part 3: Testing Strategy

### Unit Tests (Service Layer)
```java
@SpringBootTest
class TaskServiceTest {
    @MockBean
    private TaskRepository taskRepository;
    
    @InjectMocks
    private TaskService taskService;
    
    @Test
    void testCreateTask() {
        // Given
        TaskRequestDTO request = new TaskRequestDTO("Test", "TODO");
        
        // When
        TaskResponseDTO response = taskService.createTask(request);
        
        // Then
        assertNotNull(response.getId());
    }
}
```

### Integration Tests (Controller)
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void testGetAllTasks() {
        ResponseEntity<Page> response = restTemplate.getForEntity(
            "/api/tasks", Page.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
```

### API Tests (cURL/Postman)
```bash
# Test suite
./test-api.sh

# Single endpoint
curl -X GET http://localhost:8080/api/tasks
```

---

## Part 4: Monitoring & Logging

### Logging Configuration
```yaml
# application.yaml
logging:
  level:
    root: INFO
    com.likhith.taskengine: DEBUG
    org.springframework.security: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/application.log
    max-size: 10MB
    max-history: 10
```

### Health Check Endpoint
```java
@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "Task Engine API",
            "version", "1.0.0"
        ));
    }
}
```

### Metrics Collection
```yaml
# Add for monitoring
management:
  endpoints:
    web:
      exposure:
        include: health,metrics,prometheus
  metrics:
    export:
      prometheus:
        enabled: true
```

---

## Part 5: Error Handling Best Practices

### Current Implementation ✅
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("TASK_NOT_FOUND", ex.getMessage()));
    }
}
```

### Enhanced Error Handling
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(TaskNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("TASK_NOT_FOUND", ex.getMessage(), 
                System.currentTimeMillis()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
        MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse("VALIDATION_ERROR", 
                "Validation failed", errors));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Unexpected error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("INTERNAL_ERROR", 
                "An unexpected error occurred"));
    }
}
```

---

## Part 6: API Versioning Strategy

### Approach 1: URL Path Versioning
```java
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskControllerV1 { ... }

@RestController
@RequestMapping("/api/v2/tasks")
public class TaskControllerV2 { ... }
```

### Approach 2: Header-Based Versioning
```java
@GetMapping(value = "/api/tasks", headers = "API-VERSION=1")
public ResponseEntity<Page<TaskResponseDTO>> getTasksV1(...) { ... }

@GetMapping(value = "/api/tasks", headers = "API-VERSION=2")
public ResponseEntity<List<TaskResponseDTO>> getTasksV2(...) { ... }
```

---

## Part 7: Docker Deployment

### Dockerfile
```dockerfile
FROM openjdk:25-slim
WORKDIR /app
COPY target/task-engine-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Compose (with PostgreSQL)
```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: taskdb
      POSTGRES_USER: taskuser
      POSTGRES_PASSWORD: taskpass123
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  api:
    build: ./task-engine-api
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/taskdb
      SPRING_DATASOURCE_USERNAME: taskuser
      SPRING_DATASOURCE_PASSWORD: taskpass123

volumes:
  postgres_data:
```

### Build and Run
```bash
docker-compose up -d
```

---

## Part 8: CI/CD Pipeline

### GitHub Actions Workflow
```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      
      - name: Set up Java
        uses: actions/setup-java@v2
        with:
          java-version: '25'
          distribution: 'openjdk'
      
      - name: Build with Maven
        run: cd task-engine-api && ./mvnw clean package
      
      - name: Run Tests
        run: cd task-engine-api && ./mvnw test
      
      - name: SonarQube Scan
        run: ./mvnw sonar:sonar
      
      - name: Deploy to Staging
        if: github.ref == 'refs/heads/develop'
        run: ./deploy-staging.sh
      
      - name: Deploy to Production
        if: github.ref == 'refs/heads/main'
        run: ./deploy-production.sh
```

---

## Part 9: Database Migration Strategy

### Liquibase Change Log
```xml
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog">
    <changeSet id="1" author="dev">
        <createTable tableName="task">
            <column name="id" type="BIGINT" autoIncrement="true">
                <constraints primaryKey="true"/>
            </column>
            <column name="title" type="VARCHAR(255)">
                <constraints nullable="false"/>
            </column>
            <column name="status" type="VARCHAR(20)" defaultValue="TODO"/>
            <column name="created_at" type="TIMESTAMP" defaultValueDate="CURRENT_TIMESTAMP"/>
        </createTable>
    </changeSet>
</databaseChangeLog>
```

---

## Part 10: Code Quality Standards

### Checkstyle Configuration
```xml
<module name="LineLength">
    <property name="max" value="120"/>
</module>
<module name="IndentationCheck">
    <property name="basicOffset" value="4"/>
    <property name="braceAdjustment" value="4"/>
</module>
```

### SonarQube Integration
```properties
sonar.projectKey=task-engine-api
sonar.projectName=Task Engine API
sonar.sources=src/main/java
sonar.tests=src/test/java
sonar.coverage.exclusions=**/*DTO.java,**/*Entity.java
sonar.cpd.exclusions=**/*Config.java
```

---

## Part 11: Rate Limiting & Throttling

### Add Rate Limiter
```java
@Configuration
public class RateLimitingConfig {
    @Bean
    public RateLimiter taskApiRateLimiter() {
        return RateLimiter.create(100.0); // 100 requests per second
    }
}
```

### Apply to Controller
```java
@PostMapping
public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO request) {
    if (!rateLimiter.tryAcquire()) {
        throw new TooManyRequestsException("Rate limit exceeded");
    }
    return ResponseEntity.ok(taskService.createTask(request));
}
```

---

## Part 12: Feature Flags for Safe Deployments

### Feature Flag Service
```java
@Service
public class FeatureFlagService {
    public boolean isEnabled(String featureKey) {
        // Check configuration/database
        return featureFlagRepository.findByKey(featureKey)
            .map(FeatureFlag::isEnabled)
            .orElse(false);
    }
}
```

### Usage in Code
```java
if (featureFlagService.isEnabled("TASK_TAGS_FEATURE")) {
    // New feature code
} else {
    // Fallback to old behavior
}
```

---

## Summary of Improvements

| Area | Current | Recommended | Effort |
|------|---------|-------------|--------|
| Security | Basic | JWT + OAuth2 | Medium |
| Database | H2 | PostgreSQL | Medium |
| Testing | Manual | Automated | High |
| Monitoring | Logs | Prometheus + Grafana | Medium |
| Deployment | Manual | Docker + K8s | High |
| CI/CD | None | GitHub Actions | Medium |
| Code Quality | Basic | SonarQube | Low |
| Performance | Good | Caching + Indices | Medium |

---

**Recommendation**: Implement improvements in this order:
1. Add automated testing (tests are critical)
2. Switch to PostgreSQL (production stability)
3. Implement JWT authentication (security)
4. Add Docker support (deployment)
5. Set up CI/CD pipeline (automation)

---

*Last Updated: March 23, 2026*

