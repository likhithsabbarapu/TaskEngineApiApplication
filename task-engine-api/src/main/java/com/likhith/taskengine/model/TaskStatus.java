package com.likhith.taskengine.model;

/**
 * Enumeration for Task Status.
 * Enums restrict a variable to have one of only a few predefined values.
 */
public enum TaskStatus {
    TODO,         // Task is created but not started
    IN_PROGRESS,  // Task is currently being worked on
    DONE          // Task is completed
}