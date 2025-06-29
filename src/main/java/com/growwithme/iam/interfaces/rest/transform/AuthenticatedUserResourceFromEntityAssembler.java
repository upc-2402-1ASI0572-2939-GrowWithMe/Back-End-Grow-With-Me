package com.growwithme.iam.interfaces.rest.transform;

import com.growwithme.iam.domain.model.aggregates.User;
import com.growwithme.iam.domain.model.entities.Role;
import com.growwithme.iam.interfaces.rest.resources.AuthenticatedUserResource;

import java.util.List;

public class AuthenticatedUserResourceFromEntityAssembler {

    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        List<String> roles = user.getRoles().stream()
                .map(Role::getStringName)
                .toList();
        
        return new AuthenticatedUserResource(
                user.getId(),
                user.getUsername(),
                token,
                roles
        );
    }
}

