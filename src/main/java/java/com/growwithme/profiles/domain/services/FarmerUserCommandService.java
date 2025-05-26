package java.com.growwithme.profiles.domain.services;

import java.com.growwithme.profiles.domain.model.aggregates.FarmerUser;
import java.com.growwithme.profiles.domain.model.commands.farmer.CreateFarmerUserCommand;
import java.com.growwithme.profiles.domain.model.commands.farmer.DeleteFarmerUserCommand;
import java.util.Optional;

public interface FarmerUserCommandService {
    Optional<FarmerUser> handle(CreateFarmerUserCommand command);
    void handle(DeleteFarmerUserCommand command);
}
