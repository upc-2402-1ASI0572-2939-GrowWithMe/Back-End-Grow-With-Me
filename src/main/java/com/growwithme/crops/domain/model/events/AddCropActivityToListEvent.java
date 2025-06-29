package com.growwithme.crops.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import com.growwithme.crops.domain.model.aggregates.CropActivity;

@Getter
public class AddCropActivityToListEvent extends ApplicationEvent {
    private Long cropId;
    private CropActivity cropActivity;

    public AddCropActivityToListEvent(Object source, Long cropId, CropActivity cropActivity) {
        super(source);
        this.cropId = cropId;
        this.cropActivity = cropActivity;
    }
}
