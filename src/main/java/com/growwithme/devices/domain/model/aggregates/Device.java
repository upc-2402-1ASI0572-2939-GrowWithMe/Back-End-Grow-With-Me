package com.growwithme.devices.domain.model.aggregates;

import com.growwithme.crops.domain.model.aggregates.Crop;
import com.growwithme.iam.domain.model.aggregates.FarmerUser;
import com.growwithme.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class Device extends AuditableAbstractAggregateRoot<Device> {
    @ManyToOne
    @JoinColumn(name = "crop_id", nullable = false)
    private Crop crop;

    @ManyToOne
    @JoinColumn(name = "farmer_user_id", nullable = false)
    private FarmerUser farmerUser;

    private String name;

    @ElementCollection
    @CollectionTable(name = "temperature_list", joinColumns = @JoinColumn(name = "device_id"))
    @Column(name = "temperature")
    private List<Float> temperatureList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "humidity_list", joinColumns = @JoinColumn(name = "device_id"))
    @Column(name = "humidity")
    private List<Float> humidityList = new ArrayList<>();

    private Boolean isActive;

    public Device(Crop crop, FarmerUser farmerUser, String name) {
        this.crop = crop;
        this.farmerUser = farmerUser;
        this.name = name;
        this.isActive = false;
    }

    public Device() {}

    public void addTemperatureToList(Float temperature) {
        this.temperatureList.add(temperature);
    }

    public void addHumidityToList(Float humidity) {
        this.humidityList.add(humidity);
    }

    public void activateDevice() {
        this.isActive = true;
    }

}
