package com.growwithme.iam.domain.model.aggregates;

import com.growwithme.iam.domain.model.entities.Role;
import com.growwithme.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class User extends AuditableAbstractAggregateRoot<User> {
    @Email
    @NotNull
    @Column(unique = true)
    protected String email;

    @NotNull
    @Size(max = 120)
    protected String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    protected Set<Role> roles = new HashSet<>();

    @NotNull
    protected String firstName;

    @NotNull
    protected String lastName;

    @NotNull
    @Size(max = 9)
    protected String phone;

    @NotNull
    protected String photoUrl;

    @NotNull
    @Size(max = 8)
    protected String dni;

    public User() { }

    public User(String email, String password, String firstName, String lastName, String phone, String photoUrl, String dni) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.photoUrl = photoUrl;
        this.dni = dni;
        this.roles = new HashSet<>();
    }

    public User(String email, String password, String firstName, String lastName, String phone, String photoUrl, String dni, List<Role> roles) {
        this(email, password, firstName, lastName, phone, photoUrl, dni);
        addRoles(roles);
    }

    public User addRoles(List<Role> roles) {
        var validatedRoleSet = Role.validateRoleSet(roles);
        this.roles.addAll(validatedRoleSet);
        return this;
    }

    public String getUsername() {
        return email;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
