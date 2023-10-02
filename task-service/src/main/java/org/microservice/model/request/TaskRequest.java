package org.microservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.microservice.model.Task;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String title;
    private String description;
    private UUID createdBy;
    private UUID assignTo;
    private UUID groupId;

    public Task toEntity(LocalDateTime createdDate, LocalDateTime lastModified){
        return new Task(null, this.title, this.description, this.createdBy, this.assignTo, this.groupId, createdDate, lastModified);
    }

    public Task toEntity(UUID id, LocalDateTime createdDate, LocalDateTime lastModified){
        return new Task(id, this.title, this.description, this.createdBy, this.assignTo, this.groupId, createdDate, lastModified);
    }
}
