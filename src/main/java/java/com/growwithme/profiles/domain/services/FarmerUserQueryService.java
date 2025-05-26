package java.com.growwithme.profiles.domain.services;

import java.com.growwithme.profiles.domain.model.aggregates.FarmerUser;
import java.com.growwithme.profiles.domain.model.queries.farmer.GetAllFarmerUsersQuery;
import java.com.growwithme.profiles.domain.model.queries.farmer.GetFarmerUserByIdQuery;
import java.util.List;
import java.util.Optional;

public interface FarmerUserQueryService {
    List<FarmerUser> handle(GetAllFarmerUsersQuery query);
    Optional<FarmerUser> handle(GetFarmerUserByIdQuery query);
}
