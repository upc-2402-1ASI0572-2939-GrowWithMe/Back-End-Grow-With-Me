package com.growwithme.profiles.domain.services;

import com.growwithme.profiles.domain.model.aggregates.ConsultantUser;
import com.growwithme.profiles.domain.model.queries.consultant.GetAllConsultantUsersQuery;
import com.growwithme.profiles.domain.model.queries.consultant.GetConsultantUserByIdQuery;
import java.util.List;
import java.util.Optional;

public interface ConsultantUserQueryService {
    List<ConsultantUser> handle(GetAllConsultantUsersQuery query);
    Optional<ConsultantUser> handle(GetConsultantUserByIdQuery query);
}
