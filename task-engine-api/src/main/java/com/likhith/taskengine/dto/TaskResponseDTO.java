package com.likhith.taskengine.dto;

import com.likhith.taskengine.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TaskResponseDTO: Used for outgoing responses
 * Includes metadata like ID, without exposing internal details
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
}

