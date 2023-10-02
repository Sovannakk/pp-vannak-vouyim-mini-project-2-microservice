package org.microservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.microservice.dto.GroupDto;
import org.microservice.dto.TaskDto;
import org.microservice.dto.UserDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private UUID createdBy;
    private UUID assignTo;
    private UUID groupId;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;

    public TaskDto toDto(UserDto createdBy, UserDto assignTo, GroupDto groupId){
        return new TaskDto(this.id, this.title, this.description, createdBy, assignTo, groupId, this.createdDate, this.lastModified);
    }
}
