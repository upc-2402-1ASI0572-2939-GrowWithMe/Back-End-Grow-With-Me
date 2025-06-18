package com.growwithme.profiles.domain.services;

import com.growwithme.profiles.domain.model.aggregates.ConsultantUser;
import com.growwithme.profiles.domain.model.commands.consultant.CreateConsultantUserCommand;
import com.growwithme.profiles.domain.model.commands.consultant.DeleteConsultantUserCommand;
import com.growwithme.profiles.domain.model.commands.consultant.UpdateConsultantUserCommand;
import java.util.Optional;

public interface ConsultantUserCommandService {
    Optional<ConsultantUser> handle(CreateConsultantUserCommand command);
    void handle(DeleteConsultantUserCommand command);
    Optional<ConsultantUser> handle(UpdateConsultantUserCommand command);
}
