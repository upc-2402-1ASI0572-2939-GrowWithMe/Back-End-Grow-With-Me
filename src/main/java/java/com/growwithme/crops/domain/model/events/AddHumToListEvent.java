package java.com.growwithme.crops.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AddHumToListEvent extends ApplicationEvent {
    private Long cropId;
    private Float humidity;

    public AddHumToListEvent(Object source, Long cropId, Float humidity) {
        super(source);
        this.cropId = cropId;
        this.humidity = humidity;
    }
}
