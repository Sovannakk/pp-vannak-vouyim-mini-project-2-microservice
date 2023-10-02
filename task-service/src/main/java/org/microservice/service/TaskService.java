package org.microservice.service;

import org.microservice.dto.TaskDto;
import org.microservice.model.request.TaskRequest;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskDto createTask(TaskRequest taskRequest, Jwt jwt);

    List<TaskDto> getAllTask(Jwt jwt);

    TaskDto getTaskById(UUID id, Jwt jwt);

    TaskDto updateTask(UUID id, TaskRequest taskRequest, Jwt jwt);

    String deleteTask(UUID id);
}
