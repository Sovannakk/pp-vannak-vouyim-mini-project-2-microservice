package org.microservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.microservice.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin
@SecurityRequirement(name = "mini-project-auth")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("api/v1/groups")
    public ResponseEntity<?> createGroup(@RequestParam String name){
        return ResponseEntity.ok().body(groupService.createGroup(name));
    }

    @GetMapping("api/v1/groups")
    public ResponseEntity<?> getAllGroup(){
        return ResponseEntity.ok().body(groupService.getAllGroup());
    }

    @GetMapping("api/v1/groups/{groupId}/users")
    public ResponseEntity<?> getAllUserByGroupId(@PathVariable UUID groupId){
        return ResponseEntity.ok().body(groupService.getAllUserByGroupId(groupId));
    }

    @PostMapping("api/v1/groups/{groupId}/users/{userId}")
    public ResponseEntity<?> joinGroup(@PathVariable UUID groupId, @PathVariable UUID userId){
        return ResponseEntity.ok().body(groupService.joinGroup(groupId, userId));
    }

    @DeleteMapping("api/v1/groups/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable UUID id){
        return ResponseEntity.ok().body(groupService.deleteGroup(id));
    }
}
