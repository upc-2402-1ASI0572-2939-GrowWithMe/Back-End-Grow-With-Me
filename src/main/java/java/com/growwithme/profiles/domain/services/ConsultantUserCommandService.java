package java.com.growwithme.profiles.domain.services;

import java.com.growwithme.profiles.domain.model.aggregates.ConsultantUser;
import java.com.growwithme.profiles.domain.model.commands.consultant.CreateConsultantUserCommand;
import java.com.growwithme.profiles.domain.model.commands.consultant.DeleteConsultantUserCommand;
import java.util.Optional;

public interface ConsultantUserCommandService {
    Optional<ConsultantUser> handle(CreateConsultantUserCommand command);
    void handle(DeleteConsultantUserCommand command);
}
