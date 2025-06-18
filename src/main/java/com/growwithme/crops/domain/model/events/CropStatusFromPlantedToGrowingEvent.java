package com.growwithme.crops.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import com.growwithme.crops.domain.model.valueobjects.CropStatus;

@Getter
public class CropStatusFromPlantedToGrowingEvent extends ApplicationEvent {
    private Long cropId;
    private CropStatus oldCropStatus;
    private CropStatus newCropStatus;

    public CropStatusFromPlantedToGrowingEvent(Object source, Long cropId, CropStatus oldCropStatus, CropStatus newCropStatus) {
        super(source);
        this.cropId = cropId;
        this.oldCropStatus = oldCropStatus;
        this.newCropStatus = newCropStatus;
    }
}
