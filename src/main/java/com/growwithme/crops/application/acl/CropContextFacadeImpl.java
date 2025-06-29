package com.growwithme.crops.application.acl;

import com.growwithme.crops.domain.model.queries.GetCropByIdQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.growwithme.crops.application.internal.outboundservices.acl.ExternalIamService;
import com.growwithme.crops.domain.model.aggregates.Crop;
import com.growwithme.crops.domain.model.commands.CreateCropCommand;
import com.growwithme.crops.domain.model.queries.GetAllCropsByFarmerIdQuery;
import com.growwithme.crops.domain.model.valueobjects.CropCategory;
import com.growwithme.crops.domain.model.valueobjects.CropStatus;
import com.growwithme.crops.domain.services.CropCommandService;
import com.growwithme.crops.domain.services.CropQueryService;
import com.growwithme.crops.interfaces.acl.CropContextFacade;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CropContextFacadeImpl implements CropContextFacade {
    private final CropCommandService commandService;
    private final CropQueryService queryService;
    private final ExternalIamService externalIamService;

    @Override
    public Long createCrop(Long farmerId, String productName, String code, CropCategory category, Float area, String location, Float cost) {
        var cropResult = commandService.handle(new CreateCropCommand(farmerId, productName, code, category, area, location, cost));

        if (cropResult.isEmpty()) {
            throw new IllegalArgumentException("Failed to create crop for farmer with ID: " + farmerId);
        }

        return cropResult.get().getId();
    }

    @Override
    public List<Crop> fetchAllCropsByFarmerId(Long farmerId) {
        return queryService.handle(new GetAllCropsByFarmerIdQuery(farmerId));
    }

    @Override
    public Optional<Crop> fetchCropById(Long cropId) {
        return queryService.handle(new GetCropByIdQuery(cropId));
    }
}
