package java.com.growwithme.profiles.interfaces.rest.resources.consultant;

public record ConsultantUserResource(
        Long id,
        String fullName,
        String email,
        String phone,
        String dni
) {
}
