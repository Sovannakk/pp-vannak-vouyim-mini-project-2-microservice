package org.microservice.service;

import org.microservice.model.Group;
import org.microservice.model.User;
import org.microservice.response.APIResponse;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    Group createGroup(String name);

    List<Group> getAllGroup();

    APIResponse<?> joinGroup(UUID groupId, UUID userId);

    String deleteGroup(UUID id);

    List<User> getAllUserByGroupId(UUID groupId);

    Group getGroupById(UUID id);
}
