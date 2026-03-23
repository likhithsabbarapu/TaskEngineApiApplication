package com.likhith.taskengine.controller;

import com.likhith.taskengine.model.Task;
import com.likhith.taskengine.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * TaskController: The API Gateway.
 * Line-by-line Concepts:
 */
@RestController // Marks this as a REST API (Returns JSON data)
@RequestMapping("/api/tasks") // Base URL for all actions: http://localhost:8080/api/tasks
@RequiredArgsConstructor // Injects TaskService via Constructor (Dependency Injection)
@CrossOrigin(origins = "http://localhost:5173") // The "Bridge" allowing React to enter
public class TaskController {

    private final TaskService taskService;

    // GET: http://localhost:8080/api/tasks
    // This will now support the base URL without adding "/all"
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // POST: http://localhost:8080/api/tasks
    // Used when the "Add" button is clicked in React
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.saveTask(task));
    }

    // PUT: http://localhost:8080/api/tasks
    // Used for updating status or title
    @PutMapping
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.saveTask(task));
    }

    // DELETE: http://localhost:8080/api/tasks/{id}
    // @PathVariable extracts the ID from the URL (e.g., .../tasks/5)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); // Standard 204 response
    }
}