package com.growwithme.iam.interfaces.rest.resources;

import java.util.List;

public record UserResource(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String dni,
        String photoUrl,
        List<String> roles
) {
}
