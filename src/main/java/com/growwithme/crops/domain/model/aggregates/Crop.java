package com.growwithme.crops.domain.model.aggregates;

import com.growwithme.iam.domain.model.aggregates.FarmerUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import com.growwithme.crops.domain.model.valueobjects.CropCategory;
import com.growwithme.crops.domain.model.valueobjects.CropStatus;
import com.growwithme.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import java.util.List;

@Entity
@Getter
@Setter
public class Crop extends AuditableAbstractAggregateRoot<Crop> {
    @ManyToOne
    @JoinColumn(name = "farmer_user_id", nullable = false)
    @NotNull
    private FarmerUser farmerUser;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "crop_id")
    private List<CropActivity> activities;

    @NotNull
    private String productName;

    @NotNull
    private String code;

    @Enumerated(EnumType.STRING)
    private CropCategory category;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CropStatus status;

    @NotNull
    private Float area;

    @NotNull
    private String location;

    private Double maxTemperature;
    private Double minTemperature;
    private Double maxHumidity;
    private Double minHumidity;

    public Crop(FarmerUser farmerUser, String productName, String code, CropCategory category, Float area, String location) {
        this.farmerUser = farmerUser;
        this.productName = productName;
        this.code = code;
        this.category = category;
        this.status = CropStatus.EMPTY;
        this.area = area;
        this.location = location;
        this.activities = List.of();
        setDefaults();
    }

    public Crop() {}

    public String getCropStatus() {
        return status.name();
    }

    public void addActivityToList(CropActivity activity) {
        if (activity != null) {
            this.activities.add(activity);
        }
    }

    private void setDefaults() {
        if (this.category == CropCategory.VEGETABLE) {
            this.maxTemperature = 28.0;
            this.minTemperature = 15.0;
            this.maxHumidity = 80.0;
            this.minHumidity = 60.0;
        } else if (this.category == CropCategory.FRUIT) {
            this.maxTemperature = 32.0;
            this.minTemperature = 18.0;
            this.maxHumidity = 75.0;
            this.minHumidity = 50.0;
        } else if (this.category == CropCategory.HERB) {
            this.maxTemperature = 26.0;
            this.minTemperature = 16.0;
            this.maxHumidity = 75.0;
            this.minHumidity = 55.0;
        } else if (this.category == CropCategory.FLOWER) {
            this.maxTemperature = 25.0;
            this.minTemperature = 15.0;
            this.maxHumidity = 80.0;
            this.minHumidity = 60.0;
        } else if (this.category == CropCategory.GRAIN) {
            this.maxTemperature = 30.0;
            this.minTemperature = 12.0;
            this.maxHumidity = 70.0;
            this.minHumidity = 40.0;
        } else if (this.category == CropCategory.NUT) {
            this.maxTemperature = 35.0;
            this.minTemperature = 20.0;
            this.maxHumidity = 60.0;
            this.minHumidity = 40.0;
        } else if (this.category == CropCategory.LEGUME) {
            this.maxTemperature = 30.0;
            this.minTemperature = 18.0;
            this.maxHumidity = 80.0;
            this.minHumidity = 50.0;
        }
    }
}
