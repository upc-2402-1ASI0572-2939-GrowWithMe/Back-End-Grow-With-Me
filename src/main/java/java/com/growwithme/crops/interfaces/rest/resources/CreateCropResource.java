package java.com.growwithme.crops.interfaces.rest.resources;

import java.com.growwithme.crops.domain.model.valueobjects.ProfileId;

public record CreateCropResource(
        String name,
        String code,
        String category,
        Integer area,
        String location,
        String status,
        Integer cost,
        Integer profitReturn,
        ProfileId profileId
) {
}
