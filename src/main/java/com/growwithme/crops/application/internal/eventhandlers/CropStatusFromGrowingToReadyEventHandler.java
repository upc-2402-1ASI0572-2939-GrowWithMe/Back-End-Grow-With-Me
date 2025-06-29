package com.growwithme.crops.application.internal.eventhandlers;

import com.growwithme.crops.domain.model.events.CropStatusFromGrowingToReadyEvent;
import com.growwithme.crops.domain.model.valueobjects.CropStatus;
import com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CropStatusFromGrowingToReadyEventHandler {
    private final CropRepository cropRepository;

    @EventListener(CropStatusFromGrowingToReadyEvent.class)
    public void on(CropStatusFromGrowingToReadyEvent event) {
        var cropResult = cropRepository.findById(event.getCropId());
        if (cropResult.isEmpty()) {
            throw new IllegalStateException("Crop not found with ID: " + event.getCropId());
        }
        var crop = cropResult.get();

        var oldCropStatus = crop.getStatus();
        var newCropStatus = event.getNewCropStatus();

        if (!oldCropStatus.equals(CropStatus.GROWING)) {
            throw new IllegalStateException("Crop status must be GROWING to transition to READY TO HARVEST.");
        } else{
            crop.setStatus(newCropStatus);
            cropRepository.save(crop);
        }
    }
}
