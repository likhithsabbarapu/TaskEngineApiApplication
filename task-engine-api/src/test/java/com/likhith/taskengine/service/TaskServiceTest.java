package com.likhith.taskengine.service;

import com.likhith.taskengine.model.Task;
import com.likhith.taskengine.model.TaskStatus;
import com.likhith.taskengine.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TaskServiceTest: Unit tests for TaskService
 * Tests business logic without hitting the database
 */
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setDescription("Test Description");
        testTask.setStatus(TaskStatus.TODO);

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testCreateTask() {
        // Arrange
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        // Act
        Task createdTask = taskService.createTask(testTask);

        // Assert
        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getId()).isEqualTo(1L);
        assertThat(createdTask.getTitle()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).save(testTask);
    }

    @Test
    void testGetTaskById() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));

        // Act
        Task foundTask = taskService.getTaskById(1L);

        // Assert
        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getId()).isEqualTo(1L);
        assertThat(foundTask.getTitle()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaskById_NotFound() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> taskService.getTaskById(999L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Task not found");
        verify(taskRepository, times(1)).findById(999L);
    }

    @Test
    void testGetAllTasks() {
        // Arrange
        List<Task> taskList = Arrays.asList(testTask, new Task(2L, "Task 2", "Desc 2", TaskStatus.IN_PROGRESS));
        Page<Task> tasksPage = new PageImpl<>(taskList, pageable, taskList.size());
        when(taskRepository.findAll(pageable)).thenReturn(tasksPage);

        // Act
        Page<Task> result = taskService.getAllTasks(pageable);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        verify(taskRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetAllTasksList() {
        // Arrange
        List<Task> taskList = Arrays.asList(testTask);
        when(taskRepository.findAll()).thenReturn(taskList);

        // Act
        List<Task> result = taskService.getAllTasksList();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTasksByStatus() {
        // Arrange
        List<Task> taskList = Arrays.asList(testTask);
        Page<Task> tasksPage = new PageImpl<>(taskList, pageable, taskList.size());
        when(taskRepository.findByStatus(TaskStatus.TODO, pageable)).thenReturn(tasksPage);

        // Act
        Page<Task> result = taskService.getTasksByStatus(TaskStatus.TODO, pageable);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getStatus()).isEqualTo(TaskStatus.TODO);
        verify(taskRepository, times(1)).findByStatus(TaskStatus.TODO, pageable);
    }

    @Test
    void testSaveTask() {
        // Arrange
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        // Act
        Task savedTask = taskService.saveTask(testTask);

        // Assert
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isEqualTo(1L);
        verify(taskRepository, times(1)).save(testTask);
    }

    @Test
    void testDeleteTask() {
        // Arrange
        doNothing().when(taskRepository).deleteById(1L);

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository, times(1)).deleteById(1L);
    }
}

