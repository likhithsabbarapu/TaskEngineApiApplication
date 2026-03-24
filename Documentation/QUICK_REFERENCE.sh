#!/bin/bash
# Quick Reference Commands for Enterprise Task System API

echo "======================================"
echo "Enterprise Task System - Quick Guide"
echo "======================================"

# Build the project
echo ""
echo "1. BUILD PROJECT:"
echo "   cd task-engine-api"
echo "   mvn clean install"

# Run the application
echo ""
echo "2. RUN APPLICATION:"
echo "   mvn spring-boot:run"
echo "   Server starts at: http://localhost:8080"

# Access Swagger documentation
echo ""
echo "3. ACCESS DOCUMENTATION:"
echo "   Swagger UI:    http://localhost:8080/swagger-ui.html"
echo "   OpenAPI JSON:  http://localhost:8080/v3/api-docs"

# Run tests
echo ""
echo "4. RUN TESTS:"
echo "   All tests:          mvn test"
echo "   Specific test:      mvn test -Dtest=TaskServiceTest"
echo "   With coverage:      mvn test jacoco:report"

# API Examples
echo ""
echo "5. API EXAMPLES:"
echo ""
echo "   CREATE TASK:"
echo '   curl -X POST http://localhost:8080/api/tasks \'
echo '     -H "Content-Type: application/json" \'
echo '     -d '"'"'{"title":"My Task","description":"Details","status":"TODO"}'"'"''
echo ""
echo "   GET ALL TASKS (PAGINATED):"
echo '   curl "http://localhost:8080/api/tasks?page=0&size=10&sort=id,asc"'
echo ""
echo "   FILTER BY STATUS:"
echo '   curl "http://localhost:8080/api/tasks/status/TODO?page=0&size=10"'
echo ""
echo "   GET SINGLE TASK:"
echo '   curl http://localhost:8080/api/tasks/1'
echo ""
echo "   UPDATE TASK:"
echo '   curl -X PUT http://localhost:8080/api/tasks/1 \'
echo '     -H "Content-Type: application/json" \'
echo '     -d '"'"'{"title":"Updated","status":"IN_PROGRESS"}'"'"''
echo ""
echo "   DELETE TASK:"
echo '   curl -X DELETE http://localhost:8080/api/tasks/1'

# New Features
echo ""
echo "6. NEW FEATURES ADDED:"
echo "   ✅ Data Transfer Objects (DTOs)"
echo "   ✅ Entity Mapper Pattern"
echo "   ✅ Pagination & Sorting Support"
echo "   ✅ Status Filtering"
echo "   ✅ Swagger/OpenAPI Documentation"
echo "   ✅ Spring Security Configuration"
echo "   ✅ Global Exception Handling"
echo "   ✅ Enhanced Input Validation"
echo "   ✅ Comprehensive Logging"
echo "   ✅ Unit Tests (20+ cases)"
echo "   ✅ Integration Tests"
echo "   ✅ Complete Documentation"

# File Structure
echo ""
echo "7. NEW FILES CREATED:"
echo "   src/main/java/com/likhith/taskengine/"
echo "   ├── dto/"
echo "   │   ├── TaskRequestDTO.java"
echo "   │   └── TaskResponseDTO.java"
echo "   ├── mapper/"
echo "   │   └── TaskMapper.java"
echo "   ├── config/"
echo "   │   ├── SecurityConfig.java"
echo "   │   └── SwaggerConfig.java"
echo "   └── controller/"
echo "       └── GlobalExceptionHandler.java"
echo ""
echo "   src/test/java/com/likhith/taskengine/"
echo "   ├── service/"
echo "   │   └── TaskServiceTest.java"
echo "   ├── controller/"
echo "   │   └── TaskControllerTest.java"
echo "   └── mapper/"
echo "       └── TaskMapperTest.java"

# Dependencies
echo ""
echo "8. NEW DEPENDENCIES:"
echo "   - springdoc-openapi-starter-webmvc-ui:2.3.0"
echo "   - spring-boot-starter-security"
echo "   - junit-jupiter"
echo "   - mockito-core"
echo "   - mockito-junit-jupiter"
echo "   - spring-security-test"

# API Endpoints
echo ""
echo "9. API ENDPOINTS SUMMARY:"
echo "   GET    /api/tasks                      - All tasks (paginated)"
echo "   GET    /api/tasks/list                 - All tasks (no pagination)"
echo "   GET    /api/tasks/{id}                 - Get task by ID"
echo "   GET    /api/tasks/status/{status}      - Filter by status"
echo "   POST   /api/tasks                      - Create new task"
echo "   PUT    /api/tasks/{id}                 - Update task"
echo "   DELETE /api/tasks/{id}                 - Delete task"

# Pagination
echo ""
echo "10. PAGINATION PARAMETERS:"
echo "    page  - Page number (0-indexed, default: 0)"
echo "    size  - Items per page (default: 10)"
echo "    sort  - Sort criteria (format: property,direction)"
echo "    "
echo "    Example: ?page=0&size=20&sort=title,asc"

# Support
echo ""
echo "======================================"
echo "Status: ✅ ALL ENHANCEMENTS COMPLETE"
echo "Version: 2.0.0 (Enhanced)"
echo "======================================"
echo ""
echo "For detailed documentation, see:"
echo "  - ENHANCEMENTS.md"
echo "  - ENHANCEMENT_COMPLETION_SUMMARY.md"
echo ""

