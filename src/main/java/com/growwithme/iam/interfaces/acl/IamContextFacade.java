package com.growwithme.iam.interfaces.acl;

import com.growwithme.iam.domain.model.aggregates.User;

import java.util.Optional;

public interface IamContextFacade {
    Optional<User> fetchUserById(Long userId);

    Long fetchUserIdByEmail(String email);
}
