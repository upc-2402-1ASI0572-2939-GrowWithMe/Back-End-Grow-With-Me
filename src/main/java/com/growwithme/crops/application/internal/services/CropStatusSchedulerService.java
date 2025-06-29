package com.growwithme.crops.application.internal.services;

import com.growwithme.crops.domain.model.aggregates.Crop;
import com.growwithme.crops.domain.model.events.*;
import com.growwithme.crops.domain.model.valueobjects.CropStatus;
import com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropRepository;
import com.growwithme.iam.application.internal.outboundservices.acl.ExternalNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CropStatusSchedulerService {
    private final CropRepository repository;
    private final ExternalNotificationService externalNotificationService;
    private final ApplicationEventPublisher eventPublisher;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateCropStatuses() {
        List<Crop> crops = repository.findAll();
        for (Crop crop : crops) {
            CropStatus oldStatus = crop.getStatus();
            CropStatus nextStatus = getNextStatus(oldStatus);
            if (oldStatus != nextStatus) {
                publishStatusChangeEvent(crop.getId(), oldStatus, nextStatus);
                externalNotificationService.createNotification(crop.getFarmerUser().getId(), "Crop Status Updated", "Your " + crop.getProductName() + " has changed status from " + oldStatus + " to " + nextStatus);
            }
        }
    }

    private CropStatus getNextStatus(CropStatus current) {
        return switch (current) {
            case EMPTY -> CropStatus.PLANTED;
            case PLANTED -> CropStatus.GROWING;
            case GROWING -> CropStatus.READY;
            case READY -> CropStatus.HARVESTED;
            case HARVESTED -> CropStatus.HARVESTED;
        };
    }

    private void publishStatusChangeEvent(Long cropId, CropStatus oldStatus, CropStatus newStatus) {
        Object event = switch (oldStatus) {
            case EMPTY -> new CropStatusFromEmptyToPlantedEvent(this, cropId, oldStatus, newStatus);
            case PLANTED -> new CropStatusFromPlantedToGrowingEvent(this, cropId, oldStatus, newStatus);
            case GROWING -> new CropStatusFromGrowingToReadyEvent(this, cropId, oldStatus, newStatus);
            case READY -> new CropStatusFromReadyToHarvestedEvent(this, cropId, oldStatus, newStatus);
            case HARVESTED -> new CropStatusFromHarvestedToEmptyEvent(this, cropId, oldStatus, newStatus);
        };
        eventPublisher.publishEvent(event);
    }
}
