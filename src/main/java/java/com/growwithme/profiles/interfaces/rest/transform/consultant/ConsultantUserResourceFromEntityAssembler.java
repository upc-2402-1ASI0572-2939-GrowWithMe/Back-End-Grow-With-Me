package java.com.growwithme.profiles.interfaces.rest.transform.consultant;

import java.com.growwithme.profiles.domain.model.aggregates.ConsultantUser;
import java.com.growwithme.profiles.interfaces.rest.resources.consultant.ConsultantUserResource;

public class ConsultantUserResourceFromEntityAssembler {
    public static ConsultantUserResource toResourceFromEntity(ConsultantUser entity) {
        return new ConsultantUserResource(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getDni()
        );
    }
}
