package org.microservice.service.serviceImp;

import lombok.AllArgsConstructor;
import org.microservice.model.entity.Task;
import org.microservice.repository.TaskRepository;
import org.microservice.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImp implements TaskService {

    private final TaskRepository taskRepository;
    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }
}
