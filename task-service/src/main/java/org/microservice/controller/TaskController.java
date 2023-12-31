package org.microservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.microservice.model.dto.TaskDto;
import org.microservice.model.entity.Task;
import org.microservice.model.request.TaskRequest;
import org.microservice.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("/api/v1/")
@AllArgsConstructor
@SecurityRequirement(name = "mini-project-auth")
@CrossOrigin
public class TaskController {
    private final TaskService taskService;

    @PostMapping("tasks")
    public TaskDto addTask(@RequestBody TaskRequest taskRequest, @AuthenticationPrincipal Jwt jwt){
        return taskService.addNewTask(taskRequest, jwt);
    }

    @GetMapping("tasks")
    public List<TaskDto> getAllTask(@AuthenticationPrincipal Jwt jwt){
        return taskService.getAllTask(jwt);
    }

    @DeleteMapping("tasks/{id}")
    public TaskDto deleteTask(@PathVariable UUID id){
        return taskService.deleteTask(id);
    }

    @PutMapping("tasks/{id}")
    public TaskDto updateTask(@PathVariable UUID id, @RequestBody TaskRequest taskRequest, @AuthenticationPrincipal Jwt jwt){
        return taskService.updateTask(id,taskRequest,jwt);
    }

    @GetMapping("tasks/{id}")
    public TaskDto getTaskById(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt){
        return taskService.getTaskById(id,jwt);
    }

}
