package org.microservice.service;

import org.microservice.model.entity.Task;
import org.microservice.repository.TaskRepository;

import java.util.List;

public interface TaskService  {
    List<Task> getAllTask();
}
