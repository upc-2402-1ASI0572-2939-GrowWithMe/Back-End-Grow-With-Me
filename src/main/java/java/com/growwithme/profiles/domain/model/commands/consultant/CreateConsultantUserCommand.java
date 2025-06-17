package java.com.growwithme.profiles.domain.model.commands.consultant;

public record CreateConsultantUserCommand(String firstName, String lastName, String email, String phone, String photoUrl, String dni) {
}
