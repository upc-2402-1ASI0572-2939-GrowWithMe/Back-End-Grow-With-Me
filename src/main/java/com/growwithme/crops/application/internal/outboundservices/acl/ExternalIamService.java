package com.growwithme.crops.application.internal.outboundservices.acl;

import com.growwithme.iam.domain.model.aggregates.ConsultantUser;
import com.growwithme.iam.domain.model.aggregates.FarmerUser;
import com.growwithme.iam.domain.model.aggregates.User;
import com.growwithme.iam.interfaces.acl.IamContextFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ExternalIamService {
    private final IamContextFacade iamContextFacade;

    public User fetchUserById(Long userId) {
        return iamContextFacade.fetchUserById(userId).get();
    }

    public Long fetchUserIdByEmail(String email) {
        return iamContextFacade.fetchUserIdByEmail(email);
    }

    public Optional<FarmerUser> fetchFarmerUserById(Long userId) {
        var userOpt = iamContextFacade.fetchUserById(userId);
        if (userOpt.isPresent() && userOpt.get() instanceof FarmerUser farmerUser) {
            return Optional.of(farmerUser);
        }
        return Optional.empty();
    }

    public Optional<ConsultantUser> fetchConsultantUserById(Long userId) {
        var userOpt = iamContextFacade.fetchUserById(userId);
        if (userOpt.isPresent() && userOpt.get() instanceof ConsultantUser consultantUser) {
            return Optional.of(consultantUser);
        }
        return Optional.empty();
    }
}
