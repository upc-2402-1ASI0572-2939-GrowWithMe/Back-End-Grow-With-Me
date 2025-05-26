package java.com.growwithme.crops.domain.model.valueobjects;

public enum CropStatus {
    EMPTY,
    PLANTED,
    GROWING,
    READY_TO_HARVEST,
    HARVESTED;

    public static CropStatus fromString(String status) {
        for (CropStatus cropStatus : CropStatus.values()) {
            if (cropStatus.name().equalsIgnoreCase(status)) {
                return cropStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
