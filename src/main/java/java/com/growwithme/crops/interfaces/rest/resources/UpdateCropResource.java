package java.com.growwithme.crops.interfaces.rest.resources;

public record UpdateCropResource(String name, String code, String category, Integer area, String location, String status, Integer cost, Integer profitReturn) {
}
