package com.growwithme.profiles.domain.services;

import com.growwithme.profiles.domain.model.aggregates.FarmerUser;
import com.growwithme.profiles.domain.model.commands.farmer.CreateFarmerUserCommand;
import com.growwithme.profiles.domain.model.commands.farmer.DeleteFarmerUserCommand;
import com.growwithme.profiles.domain.model.commands.farmer.UpdateFarmerUserCommand;
import java.util.Optional;

public interface FarmerUserCommandService {
    Optional<FarmerUser> handle(CreateFarmerUserCommand command);
    void handle(DeleteFarmerUserCommand command);
    Optional<FarmerUser> handle(UpdateFarmerUserCommand command);
}
