package org.microservice.controller;

import lombok.AllArgsConstructor;
import org.microservice.model.entity.Task;
import org.microservice.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/v1/")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @GetMapping("task")
    List<Task> getAllTask(){
        return taskService.getAllTask();
    }
}
