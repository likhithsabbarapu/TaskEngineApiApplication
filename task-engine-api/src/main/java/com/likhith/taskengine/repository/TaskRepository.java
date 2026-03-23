package com.likhith.taskengine.repository;

import com.likhith.taskengine.model.Task;
import com.likhith.taskengine.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 1. Tells Spring this is a Data Access component
public interface TaskRepository extends JpaRepository<Task, Long> {
    // 2. JpaRepository gives us save(), findAll(), deleteById() for free.
    // 3. 'Task' is the entity, 'Long' is the type of the ID.

    // Custom query method: Spring generates the SQL based on the method name!
    // Spring Magic: This method automatically generates "SELECT * FROM tasks WHERE status = ?"
    List<Task> findByStatus(TaskStatus status);
}