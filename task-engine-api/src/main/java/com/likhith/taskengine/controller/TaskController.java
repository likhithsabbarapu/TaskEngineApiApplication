package com.likhith.taskengine.controller;

import com.likhith.taskengine.dto.TaskRequestDTO;
import com.likhith.taskengine.dto.TaskResponseDTO;
import com.likhith.taskengine.mapper.TaskMapper;
import com.likhith.taskengine.model.Task;
import com.likhith.taskengine.model.TaskStatus;
import com.likhith.taskengine.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TaskController: The API Gateway for Task Management
 * Uses DTOs to decouple API from internal entity structure
 */
@RestController // Marks this as a REST API (Returns JSON data)
@RequestMapping("/api/tasks") // Base URL for all actions: http://localhost:8080/api/tasks
@RequiredArgsConstructor // Injects TaskService and TaskMapper via Constructor (Dependency Injection)
@CrossOrigin(origins = "http://localhost:5173") // The "Bridge" allowing React to enter
@Tag(name = "Tasks", description = "Task Management API")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    // GET: http://localhost:8080/api/tasks?page=0&size=10&sort=title,asc
    // Supports pagination and sorting
    @GetMapping
    @Operation(summary = "Get all tasks with pagination and sorting", 
               description = "Retrieve all tasks with optional pagination and sorting parameters")
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        logger.info("Fetching all tasks with pagination: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Task> tasks = taskService.getAllTasks(pageable);
        Page<TaskResponseDTO> response = tasks.map(taskMapper::toResponseDTO);
        return ResponseEntity.ok(response);
    }

    // GET: http://localhost:8080/api/tasks/list
    // Get all tasks without pagination
    @GetMapping("/list")
    @Operation(summary = "Get all tasks without pagination",
               description = "Retrieve all tasks as a simple list")
    public ResponseEntity<List<TaskResponseDTO>> getAllTasksList() {
        logger.info("Fetching all tasks as a list");
        List<Task> tasks = taskService.getAllTasksList();
        List<TaskResponseDTO> response = tasks.stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // GET: http://localhost:8080/api/tasks/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID",
               description = "Retrieve a single task by its ID")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        logger.info("Fetching task with id: {}", id);
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(taskMapper.toResponseDTO(task));
    }

    // GET: http://localhost:8080/api/tasks/status/TODO?page=0&size=10
    // Filter tasks by status with pagination
    @GetMapping("/status/{status}")
    @Operation(summary = "Get tasks by status with pagination",
               description = "Retrieve tasks filtered by status with pagination support")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByStatus(
            @PathVariable TaskStatus status,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        logger.info("Fetching tasks with status: {} and pagination: page={}, size={}", status, pageable.getPageNumber(), pageable.getPageSize());
        Page<Task> tasks = taskService.getTasksByStatus(status, pageable);
        Page<TaskResponseDTO> response = tasks.map(taskMapper::toResponseDTO);
        return ResponseEntity.ok(response);
    }

    // POST: http://localhost:8080/api/tasks
    // Used when the "Add" button is clicked in React
    @PostMapping
    @Operation(summary = "Create a new task",
               description = "Create a new task with the provided details")
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO requestDTO) {
        logger.info("Creating new task: {}", requestDTO.getTitle());
        Task task = taskMapper.toTask(requestDTO);
        Task savedTask = taskService.saveTask(task);
        return ResponseEntity.ok(taskMapper.toResponseDTO(savedTask));
    }

    // PUT: http://localhost:8080/api/tasks/{id}
    // Used for updating status or title
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing task",
               description = "Update task details including title, description, and status")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO requestDTO) {
        logger.info("Updating task with id: {}", id);
        Task existingTask = taskService.getTaskById(id);
        existingTask.setTitle(requestDTO.getTitle());
        existingTask.setDescription(requestDTO.getDescription());
        if (requestDTO.getStatus() != null) {
            existingTask.setStatus(requestDTO.getStatus());
        }
        Task updatedTask = taskService.saveTask(existingTask);
        return ResponseEntity.ok(taskMapper.toResponseDTO(updatedTask));
    }

    // DELETE: http://localhost:8080/api/tasks/{id}
    // @PathVariable extracts the ID from the URL (e.g., .../tasks/5)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task",
               description = "Delete a task by its ID")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        logger.info("Deleting task with id: {}", id);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); // Standard 204 response
    }
}
