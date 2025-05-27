package java.com.growwithme.crops.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.com.growwithme.crops.domain.model.commands.CreateCropCommand;
import java.com.growwithme.crops.domain.model.commands.UpdateCropCommand;
import java.com.growwithme.crops.domain.model.valueobjects.ProfileId;
import java.com.growwithme.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Crop extends AuditableAbstractAggregateRoot<Crop> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(unique = true, nullable = false)
    private String category;

    private Integer area;

    private String location;

    private String status;

    private Integer cost;

    private Integer profitReturn;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;

    @Embedded
    private ProfileId profileId;

    @ElementCollection
    @CollectionTable(name = "crop_temperature", joinColumns = @JoinColumn(name = "crop_id"))
    @Column(name = "temperature")
    private List<String> temperature;

    @ElementCollection
    @CollectionTable(name = "crop_humidity", joinColumns = @JoinColumn(name = "crop_id"))
    @Column(name = "humidity")
    private List<String> humidity;

    public Crop() {}

    public Crop(String name, String code, String category, Integer area, String location, String status, Integer cost, Integer profitReturn, ProfileId profileId) {
        this.name = name;
        this.code = code;
        this.category = category;
        this.area = area;
        this.location = location;
        this.status = status;
        this.cost = cost;
        this.profitReturn = profitReturn;
        this.profileId = profileId;
    }

    public Crop(CreateCropCommand command) {
        this.name = command.name();
        this.code = command.code();
        this.category = command.category();
        this.area = command.area();
        this.location = command.location();
        this.status = command.status();
        this.cost = command.cost();
        this.profitReturn = command.profitReturn();
        this.profileId = new ProfileId(command.profileId().profileId());
    }

    public Crop update(String name, String code, String category, Integer area, String location, String status, Integer cost, Integer profitReturn) {
        this.name = name;
        this.code = code;
        this.category = category;
        this.area = area;
        this.location = location;
        this.status = status;
        this.cost = cost;
        this.profitReturn = profitReturn;
        return this;
    }
}
