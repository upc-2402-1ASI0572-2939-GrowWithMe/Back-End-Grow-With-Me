package com.growwithme.crops.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AddTempToListEvent extends ApplicationEvent {
    private Long cropId;
    private Float temperature;

    public AddTempToListEvent(Object source, Long cropId, Float temperature) {
        super(source);
        this.cropId = cropId;
        this.temperature = temperature;
    }
}
