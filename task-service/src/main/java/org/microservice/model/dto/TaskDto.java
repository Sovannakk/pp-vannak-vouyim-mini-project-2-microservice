package org.microservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.microservice.dto.GroupDto;
import org.microservice.dto.UserDto;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TaskDto {
    private UUID id;
    private String title;
    private String description;
    private UserDto createBy;
    private UserDto assignTo;
    private GroupDto groupId;
}
