package com.likhith.taskengine.service;

import com.likhith.taskengine.model.Task;
import com.likhith.taskengine.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service // 1. Marks this as the "Brain" of the application
@RequiredArgsConstructor // 2. Lombok: creates a constructor for the 'final' fields below
public class TaskService {

    private final TaskRepository taskRepository; // 3. The dependency we need

    public Task createTask(Task task) {
        // 4. Logic: We could check if a task with the same name exists here
        return taskRepository.save(task); // 5. Pass it to the Data Layer
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // 6. Fetch everything from the DB

        /*Key Concept: SoC (Separation of Concerns)
           Why a Service? If tomorrow you decide to send a Slack notification every
            time a task is created, you only change the Service.
          The Controller and Repository stay exactly the same.*/
    }
        public Task saveTask(Task task){
            return taskRepository.save(task);

        }

        public void deleteTask(Long id){
            taskRepository.deleteById(id);
        }

}
