package java.com.growwithme.iam.domain.model.events;

import java.com.growwithme.iam.domain.model.valueobjects.Roles;

public record UserSignedUpEvent(String email, Roles role) {
}
