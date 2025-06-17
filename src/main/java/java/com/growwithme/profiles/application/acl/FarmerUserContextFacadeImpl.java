package java.com.growwithme.profiles.application.acl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.com.growwithme.profiles.domain.model.aggregates.FarmerUser;
import java.com.growwithme.profiles.domain.model.commands.farmer.CreateFarmerUserCommand;
import java.com.growwithme.profiles.domain.model.queries.farmer.GetFarmerUserByIdQuery;
import java.com.growwithme.profiles.domain.services.FarmerUserCommandService;
import java.com.growwithme.profiles.domain.services.FarmerUserQueryService;
import java.com.growwithme.profiles.interfaces.acl.FarmerUserContextFacade;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FarmerUserContextFacadeImpl implements FarmerUserContextFacade {
    private final FarmerUserCommandService commandService;
    private final FarmerUserQueryService queryService;

    @Override
    public Long createFarmerUser(String firstName, String lastName, String email, String phone, String photoUrl, String dni) {
        var farmerUserResult = commandService.handle(new CreateFarmerUserCommand(firstName, lastName, email, phone, photoUrl, dni));

        if (farmerUserResult.isEmpty()) {
            throw new RuntimeException("Failed to create Farmer User");
        }

        return farmerUserResult.get().getId();
    }

    @Override
    public Optional<FarmerUser> fetchFarmerUserById(Long id) {
        return queryService.handle(new GetFarmerUserByIdQuery(id));
    }
}
