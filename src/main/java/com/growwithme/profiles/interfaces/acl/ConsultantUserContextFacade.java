package com.growwithme.profiles.interfaces.acl;

import com.growwithme.profiles.domain.model.aggregates.ConsultantUser;
import java.util.Optional;

public interface ConsultantUserContextFacade {
    Long createConsultantUser(String firstName, String lastName, String email, String phone, String photoUrl, String dni);

    Optional<ConsultantUser> fetchConsultantUserById(Long id);
}
