package org.microservice.service.serviceimpl;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.microservice.exception.AlreadyExistException;
import org.microservice.model.Group;
import org.microservice.model.User;
import org.microservice.model.mapper.GroupMapper;
import org.microservice.model.mapper.UserMapper;
import org.microservice.response.APIResponse;
import org.microservice.service.GroupService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public GroupServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public Group createGroup(String name) {
        GroupRepresentation groupRepresentation = prepareGroupRepresentation(name);
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        Response response = groupsResource.add(groupRepresentation);
        if(response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL){
            throw new AlreadyExistException("group is already exist");
        }
        GroupRepresentation representation = groupsResource.group(CreatedResponseUtil.getCreatedId(response)).toRepresentation();
        return GroupMapper.toDto(representation);
    }

    @Override
    public List<Group> getAllGroup() {
        List<GroupRepresentation> groupRepresentations = keycloak.realm(realm).groups().groups();
        return groupRepresentations.stream().map(GroupMapper::toDto).toList();
    }

    @Override
    public APIResponse<?> joinGroup(UUID groupId, UUID userId) {
        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.get(String.valueOf(userId)).joinGroup(String.valueOf(groupId));
        return APIResponse.builder()
                .message("successfully add user to group")
                .payload(null)
                .httpStatus(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public String deleteGroup(UUID id) {
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        groupsResource.group(String.valueOf(id)).remove();
        return "successful";
    }

    @Override
    public List<User> getAllUserByGroupId(UUID groupId) {
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        List<UserRepresentation> userRepresentations = groupsResource.group(String.valueOf(groupId)).members();
        return userRepresentations.stream().map(UserMapper::toDto).toList();
    }

    public GroupRepresentation prepareGroupRepresentation(String name){
        GroupRepresentation groupRepresentation = new GroupRepresentation();
        groupRepresentation.setName(name);
        return groupRepresentation;
    }

    @Override
    public Group getGroupById(UUID id) {
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        GroupRepresentation groupRepresentation = groupsResource.group(String.valueOf(id)).toRepresentation();
        return GroupMapper.toDto(groupRepresentation);
    }

}
