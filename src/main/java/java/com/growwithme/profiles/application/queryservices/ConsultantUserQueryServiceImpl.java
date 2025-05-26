package java.com.growwithme.profiles.application.queryservices;

import org.springframework.stereotype.Service;

import java.com.growwithme.profiles.domain.model.aggregates.ConsultantUser;
import java.com.growwithme.profiles.domain.model.queries.consultant.GetAllConsultantUsersQuery;
import java.com.growwithme.profiles.domain.model.queries.consultant.GetConsultantUserByIdQuery;
import java.com.growwithme.profiles.domain.services.ConsultantUserQueryService;
import java.com.growwithme.profiles.infrastructure.persistence.jpa.repositories.ConsultantUserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultantUserQueryServiceImpl implements ConsultantUserQueryService {
    private final ConsultantUserRepository repository;

    public ConsultantUserQueryServiceImpl(ConsultantUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ConsultantUser> handle(GetAllConsultantUsersQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<ConsultantUser> handle(GetConsultantUserByIdQuery query) {
        return repository.findById(query.id());
    }
}
