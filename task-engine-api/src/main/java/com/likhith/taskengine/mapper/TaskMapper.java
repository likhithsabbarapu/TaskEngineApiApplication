package com.likhith.taskengine.mapper;

import com.likhith.taskengine.dto.TaskRequestDTO;
import com.likhith.taskengine.dto.TaskResponseDTO;
import com.likhith.taskengine.model.Task;
import org.springframework.stereotype.Component;

/**
 * TaskMapper: Converts between Task entity and DTOs
 * Decouples the API from the database model
 */
@Component
public class TaskMapper {

    public Task toTask(TaskRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }
        Task task = new Task();
        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : task.getStatus());
        return task;
    }

    public TaskResponseDTO toResponseDTO(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskResponseDTO(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus()
        );
    }
}

