package java.com.growwithme.profiles.interfaces.rest.transform.farmer;

import java.com.growwithme.profiles.domain.model.commands.farmer.CreateFarmerUserCommand;
import java.com.growwithme.profiles.interfaces.rest.resources.farmer.CreateFarmerUserResource;

public class CreateFarmerUserCommandFromResourceAssembler {
    public static CreateFarmerUserCommand toCommandFromResource(CreateFarmerUserResource resource) {
        return new CreateFarmerUserCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.phone(),
                resource.dni()
        );
    }
}
