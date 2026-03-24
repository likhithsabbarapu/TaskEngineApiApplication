package com.likhith.taskengine.dto;

import com.likhith.taskengine.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TaskRequestDTO: Used for incoming requests
 * Separates the API contract from the database entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private TaskStatus status = TaskStatus.TODO;
}

