package com.growwithme.iam.application.acl;
import com.growwithme.iam.domain.model.aggregates.User;
import com.growwithme.iam.domain.model.queries.GetUserByIdQuery;
import com.growwithme.iam.domain.model.queries.GetUserByEmailQuery;
import com.growwithme.iam.domain.services.UserCommandService;
import com.growwithme.iam.domain.services.UserQueryService;
import com.growwithme.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IamContextFacadeImpl implements IamContextFacade {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    public IamContextFacadeImpl(UserQueryService userQueryService, UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    @Override
    public Optional<User> fetchUserById(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId));
    }

    @Override
    public Long fetchUserIdByEmail(String email) {
        var getUserByEmailQuery = new GetUserByEmailQuery(email);
        var result = userQueryService.handle(getUserByEmailQuery);
        if (result.isEmpty())
            return null;
        return result.get().getId();
    }
}
