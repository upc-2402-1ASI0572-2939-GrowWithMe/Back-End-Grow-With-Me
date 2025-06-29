package com.growwithme.iam.domain.model.aggregates;

import com.growwithme.iam.domain.model.entities.Role;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class ConsultantUser extends User {
    public ConsultantUser() {}

    public ConsultantUser(String email, String password, String firstName, String lastName, String phone, String photoUrl, String dni) {
        super(email, password, firstName, lastName, phone, photoUrl, dni);
    }

    public ConsultantUser(String email, String password, String firstName, String lastName, String phone, String photoUrl, String dni, List<Role> roles) {
        super(email, password, firstName, lastName, phone, photoUrl, dni, roles);
    }
}
