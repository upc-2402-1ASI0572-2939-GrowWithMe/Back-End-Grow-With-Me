package java.com.growwithme.profiles.interfaces.rest.resources.consultant;

public record CreateConsultantUserResource(
        String firstName,
        String lastName,
        String email,
        String phone,
        String dni
) {
}
