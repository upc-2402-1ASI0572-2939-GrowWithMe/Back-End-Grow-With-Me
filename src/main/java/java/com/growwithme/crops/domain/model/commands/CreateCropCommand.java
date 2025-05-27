package java.com.growwithme.crops.domain.model.commands;

import java.com.growwithme.crops.domain.model.valueobjects.ProfileId;
import java.util.List;

public record CreateCropCommand(String name, String code, String category, Integer area, String location, String status, Integer cost, Integer profitReturn, ProfileId profileId) {
}
