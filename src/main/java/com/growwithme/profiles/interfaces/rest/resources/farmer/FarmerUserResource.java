package com.growwithme.profiles.interfaces.rest.resources.farmer;

public record FarmerUserResource(
        Long id,
        String fullName,
        String email,
        String phone,
        String photoUrl,
        String dni
) {
}
