package com.growwithme.iam.application.internal.queryservices;

import com.growwithme.iam.domain.model.aggregates.User;
import com.growwithme.iam.domain.model.entities.Role;
import com.growwithme.iam.domain.model.queries.GetAllUsersByRoleQuery;
import com.growwithme.iam.domain.model.queries.GetUserByIdQuery;
import com.growwithme.iam.domain.model.queries.GetUserByEmailQuery;
import com.growwithme.iam.domain.services.UserQueryService;
import com.growwithme.iam.infrastructure.persistance.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findUserById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        return userRepository.findByEmail(query.email());
    }

    @Override
    public List<User> handle(GetAllUsersByRoleQuery query) {
        return userRepository.findAllByRoles_Name(query.role());
    }
}
