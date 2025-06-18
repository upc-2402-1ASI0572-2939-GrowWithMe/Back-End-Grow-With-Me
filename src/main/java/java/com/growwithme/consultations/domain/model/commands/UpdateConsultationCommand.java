package java.com.growwithme.consultations.domain.model.commands;

public record UpdateConsultationCommand(Long id, Long farmerId, String title, String description) {
}
