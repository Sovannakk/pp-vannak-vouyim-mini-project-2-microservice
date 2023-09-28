package org.microservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.microservice.model.request.UserRequest;
import org.microservice.model.request.UserUpdateRequest;
import org.microservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("api/v1/users")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok().body(userService.createUser(userRequest));
    }

    @GetMapping("api/v1/users")
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @GetMapping("api/v1/users/username")
    @SecurityRequirement(name = "mini-project-auth")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username){
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    @GetMapping("api/v1/users/email")
    @SecurityRequirement(name = "mini-project-auth")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email){
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }

    @GetMapping("api/v1/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @DeleteMapping("api/v1/users/{id}")
    @SecurityRequirement(name = "mini-project-auth")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id){
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

    @PutMapping("api/v1/users/{id}")
    @SecurityRequirement(name = "mini-project-auth")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UserUpdateRequest userUpdateRequest){
        return ResponseEntity.ok().body(userService.updateUser(id, userUpdateRequest));
    }
}
