package com.growwithme.iam.interfaces.rest.resources;

import java.util.List;

public record SignUpResource(String email, String password, List<String> roles, String firstName, String lastName, String phone, String photoUrl, String dni) {
}

