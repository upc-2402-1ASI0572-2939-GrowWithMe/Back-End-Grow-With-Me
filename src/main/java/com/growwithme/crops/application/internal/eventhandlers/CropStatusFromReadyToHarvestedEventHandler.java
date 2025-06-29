package com.growwithme.crops.application.internal.eventhandlers;

import com.growwithme.crops.domain.model.events.CropStatusFromReadyToHarvestedEvent;
import com.growwithme.crops.domain.model.valueobjects.CropStatus;
import com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CropStatusFromReadyToHarvestedEventHandler {
    private final CropRepository cropRepository;

    @EventListener(CropStatusFromReadyToHarvestedEvent.class)
    public void on(CropStatusFromReadyToHarvestedEvent event) {
        var cropResult = cropRepository.findById(event.getCropId());
        if (cropResult.isEmpty()) {
            throw new IllegalStateException("Crop not found with ID: " + event.getCropId());
        }
        var crop = cropResult.get();

        var oldCropStatus = crop.getStatus();
        var newCropStatus = event.getNewCropStatus();

        if (!oldCropStatus.equals(CropStatus.READY)) {
            throw new IllegalStateException("Crop status must be READY TO HARVEST to transition to HARVESTED.");
        } else{
            crop.setStatus(newCropStatus);
            cropRepository.save(crop);
        }
    }
}
