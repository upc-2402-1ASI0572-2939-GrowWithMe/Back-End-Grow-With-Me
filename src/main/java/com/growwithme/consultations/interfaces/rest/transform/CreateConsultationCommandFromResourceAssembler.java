package com.growwithme.consultations.interfaces.rest.transform;

import com.growwithme.consultations.domain.model.commands.CreateConsultationCommand;
import com.growwithme.consultations.interfaces.rest.resources.CreateConsultationResource;

public class CreateConsultationCommandFromResourceAssembler {
    public static CreateConsultationCommand toCommandFromResource(CreateConsultationResource resource) {
        return new CreateConsultationCommand(
                resource.farmerId(),
                resource.title(),
                resource.description()
        );
    }
}
