package java.com.growwithme.consultations.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.com.growwithme.consultations.domain.services.ConsultationCommandService;
import java.com.growwithme.consultations.domain.services.ConsultationQueryService;

@AllArgsConstructor
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/consultations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Consultations", description = "Consultations Management Endpoint")

public class ConsultationsController {
    private final ConsultationCommandService commandService;
    private final ConsultationQueryService queryService;

}
