package java.com.growwithme.iam.domain.services;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.com.growwithme.iam.domain.model.aggregates.User;
import java.com.growwithme.iam.domain.model.commands.SignInCommand;
import java.com.growwithme.iam.domain.model.commands.SignUpCommand;
import java.util.Optional;

public interface UserCommandService {
    Optional<User> handle(SignUpCommand command);
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
}
