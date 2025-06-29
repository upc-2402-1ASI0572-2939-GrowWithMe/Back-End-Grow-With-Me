package com.growwithme.iam.domain.services;

import com.growwithme.iam.domain.model.aggregates.User;
import com.growwithme.iam.domain.model.queries.GetAllUsersByRoleQuery;
import com.growwithme.iam.domain.model.queries.GetUserByIdQuery;
import com.growwithme.iam.domain.model.queries.GetUserByEmailQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByEmailQuery query);
    List<User> handle(GetAllUsersByRoleQuery query);
}
