package org.microservice.service;

import org.microservice.model.User;
import org.microservice.model.request.UserRequest;
import org.microservice.model.request.UserUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(UserRequest userRequest);

    List<User> getUserByUsername(String username);

    List<User> getUserByEmail(String email);

    String deleteUser(UUID id);

    User getUserById(UUID id);

    User updateUser(UUID id, UserUpdateRequest userUpdateRequest);

    List<User> getAllUser();
}
