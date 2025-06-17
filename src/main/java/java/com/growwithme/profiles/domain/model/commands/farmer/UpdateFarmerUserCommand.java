package java.com.growwithme.profiles.domain.model.commands.farmer;

public record UpdateFarmerUserCommand(Long id, String phone, String photoUrl) {
}
