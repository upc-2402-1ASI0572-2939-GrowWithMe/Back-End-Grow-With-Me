package com.growwithme.profiles.interfaces.acl;

import com.growwithme.profiles.domain.model.aggregates.FarmerUser;
import java.util.Optional;

public interface FarmerUserContextFacade {
    Long createFarmerUser(String firstName, String lastName, String email, String phone, String photoUrl, String dni);

    Optional<FarmerUser> fetchFarmerUserById(Long id);
}
