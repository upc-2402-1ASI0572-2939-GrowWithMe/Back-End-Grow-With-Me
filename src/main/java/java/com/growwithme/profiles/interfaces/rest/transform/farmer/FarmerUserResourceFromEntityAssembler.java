package java.com.growwithme.profiles.interfaces.rest.transform.farmer;

import java.com.growwithme.profiles.domain.model.aggregates.FarmerUser;
import java.com.growwithme.profiles.interfaces.rest.resources.farmer.FarmerUserResource;

public class FarmerUserResourceFromEntityAssembler {
    public static FarmerUserResource toResourceFromEntity(FarmerUser entity) {
        return new FarmerUserResource(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getDni()
        );
    }
}
