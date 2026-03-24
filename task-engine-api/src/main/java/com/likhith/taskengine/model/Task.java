package com.likhith.taskengine.model;

import jakarta.persistence.*; // Java Persistence API for DB mapping
import jakarta.validation.constraints.NotBlank; // For data validation
import jakarta.validation.constraints.Size; // For size validation
import lombok.AllArgsConstructor; // Lombok: Generates constructor with all fields
import lombok.Data; // Lombok: Generates Getters, Setters, toString, and Equals
import lombok.NoArgsConstructor; // Lombok: Generates empty constructor (required by Hibernate)

@Entity // Marks this class as a Database Table
@Table(name = "tasks") // Explicitly names the DB table "tasks"
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id // Defines the Primary Key for the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Database will auto-increment this ID (1, 2, 3...)
    private Long id;

    @NotBlank(message = "Title cannot be empty") // Validation: API will return 400 Error if title is null/empty
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Enumerated(EnumType.STRING) // Tells DB to store "TODO" (String) instead of 0 (Integer)
    private TaskStatus status = TaskStatus.TODO; // Default value is TODO
}