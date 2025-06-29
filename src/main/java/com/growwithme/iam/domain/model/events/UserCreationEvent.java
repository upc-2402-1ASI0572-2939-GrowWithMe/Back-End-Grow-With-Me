package com.growwithme.iam.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserCreationEvent extends ApplicationEvent {

    private Long userId;

    public UserCreationEvent(Object source, Long userId) {
        super(source);
        this.userId = userId;
    }
}
