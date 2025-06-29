package com.growwithme.iam.domain.model.commands;

import com.growwithme.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String email, String password, List<Role> roles, String firstName, String lastName, String phone, String photoUrl, String dni) {
}
