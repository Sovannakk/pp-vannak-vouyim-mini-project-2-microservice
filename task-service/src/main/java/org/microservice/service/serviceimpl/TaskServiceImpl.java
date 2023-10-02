package org.microservice.service.serviceimpl;

import lombok.AllArgsConstructor;
import org.microservice.dto.GroupDto;
import org.microservice.dto.TaskDto;
import org.microservice.dto.UserDto;
import org.microservice.exception.NotFoundException;
import org.microservice.model.Task;
import org.microservice.model.request.TaskRequest;
import org.microservice.repository.TaskRepository;
import org.microservice.service.TaskService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final WebClient.Builder webClient;

    public Mono<UserDto> getUserById(UUID userId, String token){
        return webClient.baseUrl("http://localhost:9091/users/")
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(token))
                .build()
                .get()
                .uri("{id}", userId)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    public Mono<GroupDto> getGroupById(UUID groupId, String token){
        return webClient.baseUrl("http://localhost:9091/groups/")
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(token))
                .build()
                .get()
                .uri("{id}", groupId)
                .retrieve()
                .bodyToMono(GroupDto.class);
    }

    @Override
    public TaskDto createTask(TaskRequest taskRequest, Jwt jwt) {
        return taskRepository.save(taskRequest.toEntity(LocalDateTime.now(), LocalDateTime.now())).toDto(getUserById(taskRequest.getCreatedBy(), jwt.getTokenValue()).block(), getUserById(taskRequest.getAssignTo(), jwt.getTokenValue()).block(), getGroupById(taskRequest.getGroupId(), jwt.getTokenValue()).block());
    }

    @Override
    public List<TaskDto> getAllTask(Jwt jwt) {
        return taskRepository.findAll().stream().map(task -> task.toDto(getUserById(task.getCreatedBy(), jwt.getTokenValue()).block(), getUserById(task.getAssignTo(), jwt.getTokenValue()).block(), getGroupById(task.getGroupId(), jwt.getTokenValue()).block())).toList();
    }

    @Override
    public TaskDto getTaskById(UUID id, Jwt jwt) {
        return taskRepository.findById(id).map(task -> task.toDto(getUserById(task.getCreatedBy(), jwt.getTokenValue()).block(), getUserById(task.getAssignTo(), jwt.getTokenValue()).block(), getGroupById(task.getGroupId(), jwt.getTokenValue()).block())).orElseThrow(() -> new NotFoundException("task not found"));
    }

    @Override
    public TaskDto updateTask(UUID id, TaskRequest taskRequest, Jwt jwt) {
        TaskDto taskDto = getTaskById(id, jwt);
        return taskRepository.save(taskRequest.toEntity(taskDto.getId(), taskDto.getCreatedDate(), LocalDateTime.now())).toDto(getUserById(taskRequest.getCreatedBy(), jwt.getTokenValue()).block(), getUserById(taskRequest.getAssignTo(), jwt.getTokenValue()).block(), getGroupById(taskRequest.getGroupId(), jwt.getTokenValue()).block());
    }

    @Override
    public String deleteTask(UUID id) {
        taskRepository.deleteById(id);
        return "successful";
    }
}
