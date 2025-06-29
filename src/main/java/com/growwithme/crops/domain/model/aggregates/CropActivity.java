package com.growwithme.crops.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import com.growwithme.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate activityDate;

    private String description;

    public CropActivity(Crop crop, LocalDate activityDate, String description) {
        this.crop = crop;
        this.activityDate = activityDate;
        this.description = description;
    }

    public CropActivity() {}
}
