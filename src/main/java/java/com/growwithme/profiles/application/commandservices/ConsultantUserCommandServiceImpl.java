package java.com.growwithme.profiles.application.commandservices;

import org.springframework.stereotype.Service;

import java.com.growwithme.profiles.domain.model.aggregates.ConsultantUser;
import java.com.growwithme.profiles.domain.model.commands.consultant.CreateConsultantUserCommand;
import java.com.growwithme.profiles.domain.model.commands.consultant.DeleteConsultantUserCommand;
import java.com.growwithme.profiles.domain.services.ConsultantUserCommandService;
import java.com.growwithme.profiles.infrastructure.persistence.jpa.repositories.ConsultantUserRepository;
import java.util.Optional;

@Service
public class ConsultantUserCommandServiceImpl implements ConsultantUserCommandService {
    private final ConsultantUserRepository repository;

    public ConsultantUserCommandServiceImpl(ConsultantUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ConsultantUser> handle(CreateConsultantUserCommand command) {
        var consultant = new ConsultantUser(command);
        var createdConsultant = repository.save(consultant);
        return Optional.of(createdConsultant);
    }

    @Override
    public void handle(DeleteConsultantUserCommand command) {
        repository.deleteById(command.id());
    }
}
