package java.com.growwithme.crops.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.com.growwithme.crops.domain.model.commands.CreateCropCommand;
import java.com.growwithme.crops.domain.model.valueobjects.CropCategory;
import java.com.growwithme.crops.domain.model.valueobjects.CropStatus;
import java.com.growwithme.profiles.domain.model.aggregates.FarmerUser;
import java.com.growwithme.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import java.util.List;

@Entity
@Getter
@Setter
public class Crop extends AuditableAbstractAggregateRoot<Crop> {
    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    @NotNull
    private FarmerUser farmerUser;

    @ElementCollection
    @CollectionTable(name = "crop_activities", joinColumns = @JoinColumn(name = "crop_id"))
    @Column(name = "crop_activities")
    private List<CropActivity> activities;

    @NotNull
    private String productName;

    @NotNull
    private String code;

    @Embedded
    @Enumerated(EnumType.STRING)
    private CropCategory category;

    @Embedded
    @Enumerated(EnumType.STRING)
    @NotNull
    private CropStatus status;

    @NotNull
    private Float area;

    @NotNull
    private String location;

    @NotNull
    private Float cost;

    @ElementCollection
    @CollectionTable(name = "temperature_list", joinColumns = @JoinColumn(name = "crop_id"))
    @Column(name = "temperature_list")
    private List<Float> temperatureList;

    @ElementCollection
    @CollectionTable(name = "humidity_list", joinColumns = @JoinColumn(name = "crop_id"))
    @Column(name = "humidity_list")
    private List<Float> humidityList;

    public Crop() {}

    public Crop(FarmerUser farmerUser, String productName, String code, CropCategory category, Float area, String location, Float cost) {
        this.farmerUser = farmerUser;
        this.productName = productName;
        this.code = code;
        this.category = category;
        this.status = CropStatus.EMPTY;
        this.area = area;
        this.location = location;
        this.cost = cost;
        this.activities = List.of();
        this.temperatureList = List.of();
        this.humidityList = List.of();
    }

    public String getCropStatus() {
        return status.name();
    }

    public void addActivityToList(CropActivity activity) {
        if (activity != null) {
            this.activities.add(activity);
        }
    }

    public void addTemperatureToList(Float temperature) {
        if (temperature != null) {
            this.temperatureList.add(temperature);
        }
    }

    public void addHumidityToList(Float humidity) {
        if (humidity != null) {
            this.humidityList.add(humidity);
        }
    }
}
