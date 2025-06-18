package com.growwithme.crops.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import com.growwithme.crops.domain.model.aggregates.CropActivity;

@Getter
public class AddCropActivityToList extends ApplicationEvent {
    private Long cropId;
    private CropActivity cropActivity;

    public AddCropActivityToList(Object source, Long cropId, CropActivity cropActivity) {
        super(source);
        this.cropId = cropId;
        this.cropActivity = cropActivity;
    }
}
