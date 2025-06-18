package com.growwithme.crops.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public enum CropCategory {
    VEGETABLE,
    FRUIT,
    HERB,
    FLOWER,
    GRAIN,
    NUT,
    LEGUME
}
