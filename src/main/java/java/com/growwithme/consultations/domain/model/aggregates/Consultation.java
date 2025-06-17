package java.com.growwithme.consultations.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.com.growwithme.consultations.domain.model.valueobjects.ConsultationStatus;
import java.com.growwithme.profiles.domain.model.aggregates.FarmerUser;
import java.com.growwithme.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
@Setter
public class Consultation extends AuditableAbstractAggregateRoot<Consultation> {
    @ManyToOne
    @JoinColumn(name = "farmer_id")
    @NotNull
    private FarmerUser farmerUser;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @Embedded
    @Enumerated(EnumType.STRING)
    @NotNull
    private ConsultationStatus status;

    public Consultation(FarmerUser farmerUser, String title, String description, ConsultationStatus status) {
        this.farmerUser = farmerUser;
        this.title = title;
        this.description = description;
        this.status = ConsultationStatus.PENDING;
    }
}
