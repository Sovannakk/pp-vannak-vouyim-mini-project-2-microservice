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

    @PostMapping("users")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok().body(userService.createUser(userRequest));
    }

    @GetMapping("users")
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @GetMapping("users/username")
    @SecurityRequirement(name = "mini-project-auth")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username){
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    @GetMapping("users/email")
    @SecurityRequirement(name = "mini-project-auth")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email){
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }

    @GetMapping("users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @DeleteMapping("users/{id}")
    @SecurityRequirement(name = "mini-project-auth")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id){
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

    @PutMapping("users/{id}")
    @SecurityRequirement(name = "mini-project-auth")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UserUpdateRequest userUpdateRequest){
        return ResponseEntity.ok().body(userService.updateUser(id, userUpdateRequest));
    }
}
