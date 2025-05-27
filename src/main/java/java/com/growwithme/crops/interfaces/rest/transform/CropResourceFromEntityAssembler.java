package java.com.growwithme.crops.interfaces.rest.transform;

import java.com.growwithme.crops.domain.model.aggregates.Crop;
import java.com.growwithme.crops.interfaces.rest.resources.CropResource;

public class CropResourceFromEntityAssembler {
    public static CropResource toResourceFromEntity(Crop entity) {
        return new CropResource(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getCategory(),
                entity.getArea(),
                entity.getLocation(),
                entity.getStatus(),
                entity.getCost(),
                entity.getProfitReturn(),
                entity.getProfileId()
        );
    }
}
