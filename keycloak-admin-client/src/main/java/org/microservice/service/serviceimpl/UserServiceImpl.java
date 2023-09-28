package org.microservice.service.serviceimpl;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.microservice.exception.AlreadyExistException;
import org.microservice.exception.NotFoundException;
import org.microservice.model.User;
import org.microservice.model.mapper.UserMapper;
import org.microservice.model.request.UserRequest;
import org.microservice.model.request.UserUpdateRequest;
import org.microservice.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public UserServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public User createUser(UserRequest userRequest) {
        UserRepresentation userRepresentation = prepareUserRepresentation(userRequest, preparePasswordRepresentation(userRequest.getPassword()));
        UsersResource userResource = keycloak.realm(realm).users();
        Response response = userResource.create(userRepresentation);
        if(response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL){
            throw new AlreadyExistException("user is already exist");
        }
        UserRepresentation representation = userResource.get(CreatedResponseUtil.getCreatedId(response)).toRepresentation();
        return UserMapper.toDto(representation);
    }

    @Override
    public List<User> getUserByUsername(String username) {
        List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().searchByUsername(username, false);
        return userRepresentations.stream().map(UserMapper::toDto).toList();
    }

    @Override
    public List<User> getUserByEmail(String email) {
        List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().searchByEmail(email, false);
        return userRepresentations.stream().map(UserMapper::toDto).toList();
    }

    @Override
    public String deleteUser(UUID id) {
        UsersResource usersResource = keycloak.realm(realm).users();
        Response response = usersResource.delete(id.toString());
        if(response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL){
            throw new NotFoundException("user not found");
        }
        return "successful";
    }

    @Override
    public User getUserById(UUID id) {
        UsersResource usersResource = keycloak.realm(realm).users();
        UserRepresentation userRepresentation = usersResource.get(id.toString()).toRepresentation();
        return UserMapper.toDto(userRepresentation);
    }

    @Override
    public User updateUser(UUID id, UserUpdateRequest userUpdateRequest) {
        UserRepresentation userRepresentation = prepareUserRepresentationForUpdate(userUpdateRequest, id);
        UsersResource userResource = keycloak.realm(realm).users();
        userResource.get(id.toString()).update(userRepresentation);
        return getUserById(id);
    }

    @Override
    public List<User> getAllUser() {
        List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().list();
        return userRepresentations.stream().map(UserMapper::toDto).toList();
    }

    public CredentialRepresentation preparePasswordRepresentation(String password){
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }

    public UserRepresentation prepareUserRepresentationForUpdate(UserUpdateRequest userUpdateRequest, UUID id){
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(userUpdateRequest.getEmail());
        userRepresentation.setFirstName(userUpdateRequest.getFirstName());
        userRepresentation.setLastName(userUpdateRequest.getLastName());
        userRepresentation.singleAttribute("createdDate", String.valueOf(getUserById(id).getCreatedDate()));
        userRepresentation.singleAttribute("lastModified", String.valueOf(LocalDateTime.now()));
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    public UserRepresentation prepareUserRepresentation(UserRequest userRequest, CredentialRepresentation credentialRepresentation){
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userRequest.getUsername());
        userRepresentation.setEmail(userRequest.getEmail());
        userRepresentation.setFirstName(userRequest.getFirstName());
        userRepresentation.setLastName(userRequest.getLastName());
        userRepresentation.singleAttribute("createdDate", String.valueOf(LocalDateTime.now()));
        userRepresentation.singleAttribute("lastModified", String.valueOf(LocalDateTime.now()));
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }
}
