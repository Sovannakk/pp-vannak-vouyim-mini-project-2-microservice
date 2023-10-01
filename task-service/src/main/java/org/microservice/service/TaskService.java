package org.microservice.service;

import org.microservice.model.dto.TaskDto;
import org.microservice.model.entity.Task;
import org.microservice.model.request.TaskRequest;
import org.microservice.repository.TaskRepository;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.UUID;

public interface TaskService  {

    TaskDto addNewTask(TaskRequest taskRequest, Jwt jwt);

    List<TaskDto> getAllTask(Jwt jwt);

    TaskDto deleteTask(UUID id);

    TaskDto updateTask(UUID id, TaskRequest taskRequest, Jwt jwt);

    TaskDto   getTaskById(UUID id, Jwt jwt);
}
