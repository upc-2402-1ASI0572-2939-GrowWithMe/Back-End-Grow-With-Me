package com.growwithme.iam.interfaces.rest.resources;

public record CreateUserResource(
        String email,
        String passwordHash
) {
}
