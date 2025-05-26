package java.com.growwithme.profiles.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.com.growwithme.profiles.domain.model.commands.consultant.CreateConsultantUserCommand;
import java.com.growwithme.profiles.domain.model.valueobjects.EmailAddress;
import java.com.growwithme.profiles.domain.model.valueobjects.PersonName;

@Entity
@Getter
@Setter
public class ConsultantUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PersonName name;

    @Embedded
    private EmailAddress email;

    private String phone;

    private String dni;

    public ConsultantUser(String firstName, String lastName, String email, String phone, String dni) {
        this.name = new PersonName(firstName, lastName);
        this.email = new EmailAddress(email);
        this.phone = phone;
        this.dni = dni;
    }

    public ConsultantUser(CreateConsultantUserCommand command) {
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = new EmailAddress(command.email());
        this.phone = command.phone();
        this.dni = command.dni();
    }

    public String getFullName() {
        return name.getFullName();
    }

    public String getEmail() {
        return email.email();
    }
}
