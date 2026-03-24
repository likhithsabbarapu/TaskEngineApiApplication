package com.likhith.taskengine.mapper;

import com.likhith.taskengine.dto.TaskRequestDTO;
import com.likhith.taskengine.dto.TaskResponseDTO;
import com.likhith.taskengine.model.Task;
import com.likhith.taskengine.model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TaskMapperTest: Unit tests for TaskMapper
 * Tests DTO conversion logic
 */
public class TaskMapperTest {

    private TaskMapper taskMapper;
    private Task testTask;
    private TaskRequestDTO taskRequestDTO;

    @BeforeEach
    void setUp() {
        taskMapper = new TaskMapper();
        testTask = new Task(1L, "Test Task", "Test Description", TaskStatus.TODO);
        taskRequestDTO = new TaskRequestDTO("Test Task", "Test Description", TaskStatus.TODO);
    }

    @Test
    void testToTask() {
        Task result = taskMapper.toTask(taskRequestDTO);
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Task");
        assertThat(result.getDescription()).isEqualTo("Test Description");
        assertThat(result.getStatus()).isEqualTo(TaskStatus.TODO);
    }

    @Test
    void testToResponseDTO() {
        TaskResponseDTO result = taskMapper.toResponseDTO(testTask);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Task");
        assertThat(result.getDescription()).isEqualTo("Test Description");
        assertThat(result.getStatus()).isEqualTo(TaskStatus.TODO);
    }

    @Test
    void testToTask_Null() {
        Task result = taskMapper.toTask(null);
        assertThat(result).isNull();
    }

    @Test
    void testToResponseDTO_Null() {
        TaskResponseDTO result = taskMapper.toResponseDTO(null);
        assertThat(result).isNull();
    }
}

