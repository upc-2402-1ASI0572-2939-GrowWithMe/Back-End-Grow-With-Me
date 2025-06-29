package com.growwithme.crops.application.internal.eventhandlers;

import com.growwithme.crops.domain.model.events.CropStatusFromEmptyToPlantedEvent;
import com.growwithme.crops.domain.model.valueobjects.CropStatus;
import com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CropStatusFromEmptyToPlantedEventHandler {
    private final CropRepository cropRepository;

    @EventListener(CropStatusFromEmptyToPlantedEvent.class)
    public void on(CropStatusFromEmptyToPlantedEvent event) {
        var cropResult = cropRepository.findById(event.getCropId());
        if (cropResult.isEmpty()) {
            throw new IllegalArgumentException("Crop with ID " + event.getCropId() + " does not exist.");
        }
        var crop = cropResult.get();

        var oldCropStatus = crop.getStatus();
        var newCropStatus = event.getNewCropStatus();

        if (!oldCropStatus.equals(CropStatus.EMPTY)) {
            throw new IllegalStateException("Crop status must be EMPTY to transition to PLANTED.");
        } else{
            crop.setStatus(newCropStatus);
            cropRepository.save(crop);
        }
    }
}
