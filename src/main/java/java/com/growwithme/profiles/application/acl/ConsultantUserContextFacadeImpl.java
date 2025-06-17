package java.com.growwithme.profiles.application.acl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.com.growwithme.profiles.domain.model.aggregates.ConsultantUser;
import java.com.growwithme.profiles.domain.model.commands.consultant.CreateConsultantUserCommand;
import java.com.growwithme.profiles.domain.model.queries.consultant.GetConsultantUserByIdQuery;
import java.com.growwithme.profiles.domain.services.ConsultantUserCommandService;
import java.com.growwithme.profiles.domain.services.ConsultantUserQueryService;
import java.com.growwithme.profiles.interfaces.acl.ConsultantUserContextFacade;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultantUserContextFacadeImpl implements ConsultantUserContextFacade {
    private final ConsultantUserCommandService commandService;
    private final ConsultantUserQueryService queryService;

    @Override
    public Long createConsultantUser(String firstName, String lastName, String email, String phone, String photoUrl, String dni) {
        var consultantUserResult = commandService.handle(new CreateConsultantUserCommand(firstName, lastName, email, phone, photoUrl, dni));

        if (consultantUserResult.isEmpty()) {
            throw new RuntimeException("Failed to create Consultant User");
        }

        return consultantUserResult.get().getId();
    }

    @Override
    public Optional<ConsultantUser> fetchConsultantUserById(Long id) {
        return queryService.handle(new GetConsultantUserByIdQuery(id));
    }
}
