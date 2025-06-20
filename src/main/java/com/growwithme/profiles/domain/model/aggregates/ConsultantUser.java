package com.growwithme.profiles.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import com.growwithme.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
@Setter
public class ConsultantUser extends AuditableAbstractAggregateRoot<ConsultantUser> {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private String photoUrl;

    @NotNull
    private String dni;

    public ConsultantUser(String firstName, String lastName, String email, String phone, String photoUrl, String dni) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.photoUrl = photoUrl;
        this.dni = dni;
    }

    public ConsultantUser() {}

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
