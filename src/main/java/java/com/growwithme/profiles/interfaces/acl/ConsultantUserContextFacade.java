package java.com.growwithme.profiles.interfaces.acl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.com.growwithme.profiles.domain.model.commands.consultant.CreateConsultantUserCommand;
import java.com.growwithme.profiles.domain.services.ConsultantUserCommandService;
import java.com.growwithme.profiles.domain.services.ConsultantUserQueryService;

@Service
@AllArgsConstructor
public class ConsultantUserContextFacade {
    private final ConsultantUserCommandService commandService;
    private final ConsultantUserQueryService queryService;

    public Long createConsultantUser(String firstName, String lastName, String email, String phone, String dni) {
        var createConsultantUserCommand = new CreateConsultantUserCommand(firstName, lastName, email, phone, dni);
        var consultantUser = commandService.handle(createConsultantUserCommand);
        if (consultantUser.isEmpty()) return 0L;
        return consultantUser.get().getId();
    }
}
