package com.growwithme.iam.application.internal.commandservices;

import com.growwithme.iam.application.internal.outboundservices.hashing.HashingService;
import com.growwithme.iam.application.internal.outboundservices.tokens.TokenService;
import com.growwithme.iam.domain.model.aggregates.ConsultantUser;
import com.growwithme.iam.domain.model.aggregates.FarmerUser;
import com.growwithme.iam.domain.model.aggregates.User;
import com.growwithme.iam.domain.model.commands.SignInCommand;
import com.growwithme.iam.domain.model.commands.SignUpCommand;
import com.growwithme.iam.domain.model.events.UserCreationEvent;
import com.growwithme.iam.domain.model.valueobjects.Roles;
import com.growwithme.iam.domain.services.UserCommandService;
import com.growwithme.iam.infrastructure.persistance.jpa.repositories.RoleRepository;
import com.growwithme.iam.infrastructure.persistance.jpa.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserCommandServicesImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher eventPublisher;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserCommandServicesImpl.class);

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty())
            throw new RuntimeException("Email not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");

        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByEmail(command.email()))
            throw new RuntimeException("Email already exists");
        var roles = command.roles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Role name not found")))
                .toList();

        User user;
        if (roles.stream().anyMatch(r -> r.getName() == Roles.FARMER_ROLE)) {
            user = new FarmerUser(command.email(), hashingService.encode(command.password()), command.firstName(), command.lastName(), command.phone(), command.photoUrl(), command.dni(), roles);
        } else if (roles.stream().anyMatch(r -> r.getName() == Roles.CONSULTANT_ROLE)) {
            user = new ConsultantUser(command.email(), hashingService.encode(command.password()), command.firstName(), command.lastName(), command.phone(), command.photoUrl(), command.dni(), roles);
        } else {
            throw new RuntimeException("Invalid role for user");
        }

        var savedUser = userRepository.save(user);
        eventPublisher.publishEvent(new UserCreationEvent(savedUser, savedUser.getId()));
        return userRepository.findByEmail(command.email());
    }
}
