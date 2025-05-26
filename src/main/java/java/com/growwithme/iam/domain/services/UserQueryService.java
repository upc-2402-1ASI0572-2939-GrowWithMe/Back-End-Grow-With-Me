package java.com.growwithme.iam.domain.services;

import java.com.growwithme.iam.domain.model.aggregates.User;
import java.com.growwithme.iam.domain.model.queries.GetAllUsersQuery;
import java.com.growwithme.iam.domain.model.queries.GetUserByEmailQuery;
import java.com.growwithme.iam.domain.model.queries.GetUserByIdQuery;
import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByEmailQuery query);
}
