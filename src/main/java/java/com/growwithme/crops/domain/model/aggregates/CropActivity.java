package java.com.growwithme.crops.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.com.growwithme.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import java.util.Date;

@Entity
@Getter
@Setter
public class CropActivity extends AuditableAbstractAggregateRoot<CropActivity> {
    @ManyToOne
    @JoinColumn(name = "crop_id")
    @NotNull
    private Crop crop;

    @NotNull
    private Date activityDate;

    private String description;

    public CropActivity(Crop crop, Date activityDate, String description) {
        this.crop = crop;
        this.activityDate = activityDate;
        this.description = description;
    }
}
