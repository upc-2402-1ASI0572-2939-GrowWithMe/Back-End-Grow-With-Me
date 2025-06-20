package com.growwithme.notifications.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import com.growwithme.profiles.domain.model.aggregates.FarmerUser;
import com.growwithme.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
@Setter
public class Notification extends AuditableAbstractAggregateRoot<Notification> {
    @ManyToOne
    @JoinColumn(name = "farmer_user_id")
    @NotNull
    private FarmerUser farmerUser;

    @NotNull
    private String title;

    @NotNull
    private String message;

    public Notification(FarmerUser farmerUser, String title, String message) {
        this.farmerUser = farmerUser;
        this.title = title;
        this.message = message;
    }

    public Notification() {}
}
