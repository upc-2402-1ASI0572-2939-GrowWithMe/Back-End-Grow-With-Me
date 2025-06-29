package com.growwithme.consultations.interfaces.rest.transform;

import com.growwithme.consultations.domain.model.commands.UpdateConsultationCommand;
import com.growwithme.consultations.interfaces.rest.resources.UpdateConsultationResource;

public class UpdateConsultationCommandFromResourceAssembler {
    public static UpdateConsultationCommand toCommandFromResource(Long id, UpdateConsultationResource resource) {
        return new UpdateConsultationCommand(
                id,
                resource.title(),
                resource.description()
        );
    }
}
