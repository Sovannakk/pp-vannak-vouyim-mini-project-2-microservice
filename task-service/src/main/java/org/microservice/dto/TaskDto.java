package org.microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private UUID id;
    private String title;
    private String description;
    private UserDto createdBy;
    private UserDto assignTo;
    private GroupDto groupId;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;
}
