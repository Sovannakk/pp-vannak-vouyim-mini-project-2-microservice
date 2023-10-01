package org.microservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.microservice.dto.GroupDto;
import org.microservice.dto.UserDto;
import org.microservice.model.dto.TaskDto;


import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private UUID createBy;
    private UUID assignTo;
    private UUID groupId;

    public Task(String title, String description, UUID createBy, UUID assignTo, UUID groupId) {
        this.title = title;
        this.description = description;
        this.createBy = createBy;
        this.assignTo = assignTo;
        this.groupId = groupId;
    }

//    public Task(UUID id,String title, String description, UUID createBy, UUID assignTo, UUID groupId) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.createBy = createBy;
//        this.assignTo = assignTo;
//        this.groupId = groupId;
//    }

    public TaskDto toDto(UserDto createBy, UserDto assignTo, GroupDto groupId){
//        return new TaskDto(this.id,this.title,this.description,this.createBy,this.assignTo,this.groupId);
        TaskDto  taskDto = new TaskDto();
        taskDto.setId(id);
        taskDto.setTitle(title);
        taskDto.setDescription(description);
        taskDto.setCreateBy(createBy);
        taskDto.setAssignTo(assignTo);
        taskDto.setGroupId(groupId);

        return taskDto;
    }

}
