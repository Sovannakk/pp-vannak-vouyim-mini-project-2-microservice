package org.microservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.microservice.model.entity.Task;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TaskRequest {

    private String title;
    private String description;
    private UUID createBy;
    private UUID assignTo;
    private UUID groupId;

    public Task toEntity(){
        return new Task(this.title,this.description,this.createBy,this.assignTo,this.groupId);
    }

    public Task toEntity(UUID id){
        return new Task(id,this.title,this.description,this.createBy,this.assignTo,this.groupId);
    }
}
