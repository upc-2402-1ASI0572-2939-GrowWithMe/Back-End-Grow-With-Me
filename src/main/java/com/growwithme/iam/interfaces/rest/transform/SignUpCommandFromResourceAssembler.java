package com.growwithme.iam.interfaces.rest.transform;

import com.growwithme.iam.domain.model.commands.SignUpCommand;
import com.growwithme.iam.domain.model.entities.Role;
import com.growwithme.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;

public class SignUpCommandFromResourceAssembler {

  public static SignUpCommand toCommandFromResource(SignUpResource resource) {
    var roles = resource.roles() != null
        ? resource.roles().stream().map(Role::toRoleFromName).toList()
        : new ArrayList<Role>();
    return new SignUpCommand(
            resource.email(),
            resource.password(),
            roles,
            resource.firstName(),
            resource.lastName(),
            resource.phone(),
            resource.photoUrl(),
            resource.dni()
    );
  }
}
