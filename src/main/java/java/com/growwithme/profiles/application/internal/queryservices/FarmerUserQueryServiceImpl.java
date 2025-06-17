package java.com.growwithme.profiles.application.internal.queryservices;

import org.springframework.stereotype.Service;

import java.com.growwithme.profiles.domain.model.aggregates.FarmerUser;
import java.com.growwithme.profiles.domain.model.queries.farmer.GetAllFarmerUsersQuery;
import java.com.growwithme.profiles.domain.model.queries.farmer.GetFarmerUserByIdQuery;
import java.com.growwithme.profiles.domain.services.FarmerUserQueryService;
import java.com.growwithme.profiles.infrastructure.persistence.jpa.repositories.FarmerUserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class FarmerUserQueryServiceImpl implements FarmerUserQueryService {
    private final FarmerUserRepository repository;

    public FarmerUserQueryServiceImpl(FarmerUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FarmerUser> handle(GetAllFarmerUsersQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<FarmerUser> handle(GetFarmerUserByIdQuery query) {
        return repository.findById(query.id());
    }
}
