package java.com.growwithme.profiles.interfaces.acl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.com.growwithme.profiles.domain.model.commands.farmer.CreateFarmerUserCommand;
import java.com.growwithme.profiles.domain.services.FarmerUserCommandService;
import java.com.growwithme.profiles.domain.services.FarmerUserQueryService;

@Service
@AllArgsConstructor
public class FarmerUserContextFacade {
    private final FarmerUserCommandService commandService;
    private final FarmerUserQueryService queryService;

    public Long createFarmerUser(String firstName, String lastName, String email, String phone, String dni) {
        var createFarmerUserCommand = new CreateFarmerUserCommand(firstName, lastName, email, phone, dni);
        var farmerUser = commandService.handle(createFarmerUserCommand);
        if (farmerUser.isEmpty()) return 0L;
        return farmerUser.get().getId();
    }
}
