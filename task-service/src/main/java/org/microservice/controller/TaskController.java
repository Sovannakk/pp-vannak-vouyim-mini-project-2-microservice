package org.microservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.microservice.model.request.TaskRequest;
import org.microservice.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
@SecurityRequirement(name = "mini-project-auth")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("tasks")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest, @AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.ok().body(taskService.createTask(taskRequest, jwt));
    }

    @GetMapping("tasks")
    public ResponseEntity<?> getAllTask(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.ok().body(taskService.getAllTask(jwt));
    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.ok().body(taskService.getTaskById(id, jwt));
    }

    @PutMapping("tasks/{id}")
    public ResponseEntity<?> updateTask(@PathVariable UUID id, @RequestBody TaskRequest taskRequest, @AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.ok().body(taskService.updateTask(id, taskRequest, jwt));
    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID id){
        return ResponseEntity.ok().body(taskService.deleteTask(id));
    }
}
