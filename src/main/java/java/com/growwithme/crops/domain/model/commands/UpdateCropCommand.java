package java.com.growwithme.crops.domain.model.commands;

public record UpdateCropCommand(Long id, String name, String code, String category, Integer area, String location, String status, Integer cost, Integer profitReturn) {
}
