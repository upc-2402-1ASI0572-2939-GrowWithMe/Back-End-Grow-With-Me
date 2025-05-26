package java.com.growwithme.profiles.interfaces.rest.transform.consultant;

import java.com.growwithme.profiles.domain.model.commands.consultant.CreateConsultantUserCommand;
import java.com.growwithme.profiles.interfaces.rest.resources.consultant.CreateConsultantUserResource;

public class CreateConsultantUserCommandFromResourceAssembler {
    public static CreateConsultantUserCommand toCommandFromResource(CreateConsultantUserResource resource) {
        return new CreateConsultantUserCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.phone(),
                resource.dni()
        );
    }
}
