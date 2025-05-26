package java.com.growwithme.iam.application.internal.commandservices;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.com.growwithme.iam.application.internal.outboundservices.hashing.HashingService;
import java.com.growwithme.iam.application.internal.outboundservices.tokens.TokenService;
import java.com.growwithme.iam.domain.model.aggregates.User;
import java.com.growwithme.iam.domain.model.commands.SignInCommand;
import java.com.growwithme.iam.domain.model.commands.SignUpCommand;
import java.com.growwithme.iam.domain.model.events.UserSignedUpEvent;
import java.com.growwithme.iam.domain.services.UserCommandService;
import java.com.growwithme.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import java.com.growwithme.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (command.role() == null || !roleRepository.existsByName(command.role())) {
            throw new RuntimeException("Invalid role provided");
        }
        if (userRepository.existsByEmail(command.email()))
            throw new RuntimeException("Username already exists");
        var role = roleRepository.findByName(command.role())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        var user = new User(command.email(), hashingService.encode(command.password()), List.of(role));
        userRepository.save(user);

        applicationEventPublisher.publishEvent(new UserSignedUpEvent(command.email(), command.role()));

        return userRepository.findByEmail(command.email());
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty()) throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
        var currentUser = user.get();
        var token = tokenService.generateToken(currentUser.getEmail());
        return Optional.of(ImmutablePair.of(currentUser, token));
    }
}
