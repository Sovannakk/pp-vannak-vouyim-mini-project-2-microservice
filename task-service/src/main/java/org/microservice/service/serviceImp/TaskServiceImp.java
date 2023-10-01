package org.microservice.service.serviceImp;

import org.microservice.dto.GroupDto;
import org.microservice.dto.UserDto;
import org.microservice.model.dto.TaskDto;
import org.microservice.model.entity.Task;
import org.microservice.model.request.TaskRequest;
import org.microservice.repository.TaskRepository;
import org.microservice.service.TaskService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImp implements TaskService {

    private final TaskRepository taskRepository;
    private final WebClient webClient;

    public TaskServiceImp(TaskRepository taskRepository, WebClient.Builder webClientBuilder) {
        this.taskRepository = taskRepository;
        this.webClient = webClientBuilder.baseUrl("http://localhost:9091/").build();
    }

    public Mono<UserDto> getUserById(UUID id, String token){
        return webClient.get()
                .uri("users/{id}",id)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    public Mono<GroupDto> getGroupById(UUID id, String token){
        return webClient.get()
                .uri("groups/{id}",id)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(GroupDto.class);
    }

    @Override
    public TaskDto addNewTask(TaskRequest taskRequest, Jwt jwt) {
        var taskEntity = taskRequest.toEntity();
        var createBy = getUserById(taskRequest.getCreateBy(), jwt.getTokenValue()).block();
        var assignTo = getUserById(taskRequest.getAssignTo(), jwt.getTokenValue()).block();
        var groupId = getGroupById(taskRequest.getGroupId(), jwt.getTokenValue()).block();

        return taskRepository.save(taskEntity).toDto(createBy,assignTo,groupId);
    }

    @Override
    public List<TaskDto> getAllTask(Jwt jwt) {
       var task = taskRepository.findAll();
       List<TaskDto> taskDto = new ArrayList<>();

       for(Task ta : task){
           var createBy = getUserById(ta.getCreateBy(), jwt.getTokenValue()).block();
           var assignTo = getUserById(ta.getAssignTo(), jwt.getTokenValue()).block();
           var groupId  = getGroupById(ta.getGroupId(), jwt.getTokenValue()).block();

           taskDto.add(ta.toDto(createBy,assignTo,groupId));
       }
       return taskDto;
    }

    @Override
    public TaskDto deleteTask(UUID id) {
        Task taskId = taskRepository.findById(id).orElse(null);
        if(taskId != null){
            taskRepository.deleteById(taskId.getId());
        }
        return null;
    }

    @Override
    public TaskDto updateTask(UUID id, TaskRequest taskRequest, Jwt jwt) {
        Task taskId = taskRepository.findById(id).orElse(null);
        if(taskId != null){
            var taskEntity = taskRequest.toEntity(taskId.getId());
            var createBy  = getUserById(taskId.getCreateBy(), jwt.getTokenValue()).block();
            var assignTo  = getUserById(taskId.getAssignTo(), jwt.getTokenValue()).block();
            var groupId   = getGroupById(taskId.getGroupId(), jwt.getTokenValue()).block();

            return taskRepository.save(taskEntity).toDto(createBy,assignTo,groupId);
        }
        return null;
    }

    @Override
    public TaskDto getTaskById(UUID id, Jwt jwt) {

        TaskDto taskDto = new TaskDto();

        var task = taskRepository.findById(id);
        var createBy = getUserById(task.get().getCreateBy(), jwt.getTokenValue()).block();
        var assignTo = getUserById(task.get().getAssignTo(), jwt.getTokenValue()).block();
        var groupId  = getGroupById(task.get().getGroupId(), jwt.getTokenValue()).block();

        taskDto.setId(task.get().getId());
        taskDto.setTitle(task.get().getTitle());
        taskDto.setDescription(task.get().getDescription());
        taskDto.setCreateBy(createBy);
        taskDto.setAssignTo(assignTo);
        taskDto.setGroupId(groupId);
        return taskDto;
    }


}
