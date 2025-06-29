package com.growwithme.crops.application.internal.eventhandlers;

import com.growwithme.crops.domain.model.events.CropStatusFromPlantedToGrowingEvent;
import com.growwithme.crops.domain.model.valueobjects.CropStatus;
import com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CropStatusFromPlantedToGrowingEventHandler {
    private final CropRepository cropRepository;

    @EventListener(CropStatusFromPlantedToGrowingEvent.class)
    public void on(CropStatusFromPlantedToGrowingEvent event) {
        var cropResult = cropRepository.findById(event.getCropId());
        if (cropResult.isEmpty()) {
            throw new IllegalStateException("Crop not found with ID: " + event.getCropId());
        }
        var crop = cropResult.get();

        var oldCropStatus = crop.getStatus();
        var newCropStatus = event.getNewCropStatus();

        if (!oldCropStatus.equals(CropStatus.PLANTED)) {
            throw new IllegalStateException("Crop status must be PLANTED to transition to GROWING.");
        } else{
            crop.setStatus(newCropStatus);
            cropRepository.save(crop);
        }
    }
}
