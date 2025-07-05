package com.growwithme.iam.interfaces.rest.transform;

import com.growwithme.iam.domain.model.aggregates.User;
import com.growwithme.iam.domain.model.entities.Role;
import com.growwithme.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {

    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream()
                .map(Role::getStringName)
                .toList();

        return new UserResource(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getDni(),
                user.getPhotoUrl(),
                roles
        );
    }
}
