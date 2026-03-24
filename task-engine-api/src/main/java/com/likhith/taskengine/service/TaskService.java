package com.likhith.taskengine.service;

import com.likhith.taskengine.model.Task;
import com.likhith.taskengine.model.TaskStatus;
import com.likhith.taskengine.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 1. Marks this as the "Brain" of the application
@RequiredArgsConstructor // 2. Lombok: creates a constructor for the 'final' fields below
public class TaskService {

    private final TaskRepository taskRepository; // 3. The dependency we need

    /**
     * Create a new task
     */
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Get all tasks with pagination and sorting
     */
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    /**
     * Get all tasks (without pagination)
     */
    public List<Task> getAllTasksList() {
        return taskRepository.findAll();
    }

    /**
     * Get task by ID
     */
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    /**
     * Get tasks filtered by status with pagination
     */
    public Page<Task> getTasksByStatus(TaskStatus status, Pageable pageable) {
        return taskRepository.findByStatus(status, pageable);
    }

    /**
     * Save or update task
     */
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Delete task by ID
     */
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
