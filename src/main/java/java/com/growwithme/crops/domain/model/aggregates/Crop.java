package java.com.growwithme.crops.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.com.growwithme.crops.domain.model.commands.CreateCropCommand;
import java.com.growwithme.crops.domain.model.valueobjects.CropStatus;
import java.com.growwithme.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import java.util.List;

@Entity
@Getter
@Setter
public class Crop extends AuditableAbstractAggregateRoot<Crop> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @Embedded
    @Enumerated(EnumType.STRING)
    private CropStatus status;

    @ElementCollection
    @CollectionTable(name = "crop_temperature", joinColumns = @JoinColumn(name = "crop_id"))
    @Column(name = "temperature")
    private List<String> temperature;

    @ElementCollection
    @CollectionTable(name = "crop_humidity", joinColumns = @JoinColumn(name = "crop_id"))
    @Column(name = "humidity")
    private List<String> humidity;

    public Crop() {}

    public Crop(String name) {
        this.name = name;
        this.status = CropStatus.EMPTY;
    }

    public Crop(CreateCropCommand command) {
        this.name = command.name();
        this.status = CropStatus.EMPTY;
    }

    public String getCropStatus() {
        return status.name();
    }

    public void toPlantedFromEmpty() {
        if (this.status != CropStatus.EMPTY) {
            throw new IllegalStateException("The crop must be in EMPTY state to change to PLANTED.");
        }
        this.status = CropStatus.PLANTED;
    }

    public void toGrowingFromPlanted() {
        if (this.status != CropStatus.PLANTED) {
            throw new IllegalStateException("The crop must be in PLANTED state to change to GROWING.");
        }
        this.status = CropStatus.GROWING;
    }

    public void toReadyToHarvestFromGrowing() {
        if (this.status != CropStatus.GROWING) {
            throw new IllegalStateException("The crop must be in GROWING state to change to READY_TO_HARVEST.");
        }
        this.status = CropStatus.READY_TO_HARVEST;
    }

    public void toHarvestedFromReadyToHarvest() {
        if (this.status != CropStatus.READY_TO_HARVEST) {
            throw new IllegalStateException("The crop must be in READY_TO_HARVEST state to change to HARVESTED.");
        }
        this.status = CropStatus.HARVESTED;
    }

    public void toEmptyFromHarvested() {
        if (this.status != CropStatus.HARVESTED) {
            throw new IllegalStateException("The crop must be in HARVESTED state to change to EMPTY.");
        }
        this.status = CropStatus.EMPTY;
    }
}
