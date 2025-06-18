package com.growwithme.profiles.interfaces.rest.transform.consultant;

import com.growwithme.profiles.domain.model.commands.consultant.CreateConsultantUserCommand;
import com.growwithme.profiles.interfaces.rest.resources.consultant.CreateConsultantUserResource;

public class CreateConsultantUserCommandFromResourceAssembler {
    public static CreateConsultantUserCommand toCommandFromResource(CreateConsultantUserResource resource) {
        return new CreateConsultantUserCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.phone(),
                resource.photoUrl(),
                resource.dni()
        );
    }
}
