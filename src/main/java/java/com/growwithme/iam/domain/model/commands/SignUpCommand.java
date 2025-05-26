package java.com.growwithme.iam.domain.model.commands;

import java.com.growwithme.iam.domain.model.valueobjects.Roles;

public record SignUpCommand(String firstName, String lastName, String email, String phone, String dni, String password, Roles role) {
}
