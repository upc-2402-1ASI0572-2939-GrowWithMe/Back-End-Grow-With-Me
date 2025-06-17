package java.com.growwithme.crops.application.internal.outboundservices.acl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.com.growwithme.profiles.domain.model.aggregates.FarmerUser;
import java.com.growwithme.profiles.interfaces.acl.FarmerUserContextFacade;

@Service
@AllArgsConstructor
public class ExternalFarmerUserService {
    private final FarmerUserContextFacade farmerUserContextFacade;

    public FarmerUser fetchFarmerUserById(Long farmerId) {
        return farmerUserContextFacade.fetchFarmerUserById(farmerId).get();
    }
}
