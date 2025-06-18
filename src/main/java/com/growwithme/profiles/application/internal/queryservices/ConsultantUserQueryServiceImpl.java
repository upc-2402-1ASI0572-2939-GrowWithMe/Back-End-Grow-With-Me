package com.growwithme.profiles.application.internal.queryservices;

import org.springframework.stereotype.Service;

import com.growwithme.profiles.domain.model.aggregates.ConsultantUser;
import com.growwithme.profiles.domain.model.queries.consultant.GetAllConsultantUsersQuery;
import com.growwithme.profiles.domain.model.queries.consultant.GetConsultantUserByIdQuery;
import com.growwithme.profiles.domain.services.ConsultantUserQueryService;
import com.growwithme.profiles.infrastructure.persistence.jpa.repositories.ConsultantUserRepository;
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
