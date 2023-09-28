package org.microservice.model.mapper;

import org.keycloak.representations.idm.GroupRepresentation;
import org.microservice.model.Group;

import java.util.UUID;

public class GroupMapper {
    public static Group toDto(GroupRepresentation groupRepresentation) {
        Group group = new Group();
        group.setId(UUID.fromString(groupRepresentation.getId()));
        group.setName(groupRepresentation.getName());
        return group;
    }
}
