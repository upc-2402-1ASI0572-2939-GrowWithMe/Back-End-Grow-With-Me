package com.growwithme.devices.application.internal.outboundservices.acl;

import com.growwithme.crops.domain.model.aggregates.Crop;
import com.growwithme.crops.interfaces.acl.CropContextFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExternalCropService {
    private final CropContextFacade cropContextFacade;

    public Crop fetchCropById(Long cropId) {
        return cropContextFacade.fetchCropById(cropId).get();
    }
}
