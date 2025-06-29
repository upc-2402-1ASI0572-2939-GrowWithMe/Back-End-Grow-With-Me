package com.growwithme.consultations.interfaces.rest;

import com.growwithme.consultations.domain.model.commands.CreateConsultationCommand;
import com.growwithme.consultations.domain.model.commands.DeleteConsultationCommand;
import com.growwithme.consultations.domain.model.queries.GetAllConsultationsByFarmerIdQuery;
import com.growwithme.consultations.domain.model.queries.GetAllConsultationsQuery;
import com.growwithme.consultations.interfaces.rest.resources.ConsultationResource;
import com.growwithme.consultations.interfaces.rest.resources.CreateConsultationResource;
import com.growwithme.consultations.interfaces.rest.transform.ConsultationResourceFromEntityAssembler;
import com.growwithme.consultations.interfaces.rest.transform.CreateConsultationCommandFromResourceAssembler;
import com.growwithme.crops.application.internal.outboundservices.acl.ExternalIamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.growwithme.consultations.domain.services.ConsultationCommandService;
import com.growwithme.consultations.domain.services.ConsultationQueryService;

import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/consultations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Consultations", description = "Consultations Management Endpoint")

public class ConsultationsController {
    private final ConsultationCommandService commandService;
    private final ConsultationQueryService queryService;
    private final ExternalIamService externalIamService;

    @Operation(summary = "Create a new consultation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consultation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ConsultationResource> createConsultation(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("title") String title, @RequestParam("description") String description) {
        var email = userDetails.getUsername();
        var farmerId = externalIamService.fetchUserIdByEmail(email);

        if (farmerId == null) {
            throw new IllegalArgumentException("Farmer ID not found for the provided email");
        }

        var creatConsultationCommand = new CreateConsultationCommand(
                farmerId,
                title,
                description
        );

        var consultation = commandService.handle(creatConsultationCommand);

        if (consultation.isEmpty()) {
            throw new IllegalArgumentException("Invalid input data for creating consultation");
        }

        var consultationResource = ConsultationResourceFromEntityAssembler.toResourceFromEntity(consultation.get());
        return new ResponseEntity<>(consultationResource, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a consultation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consultation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Consultation not found")
    })
    @DeleteMapping("/{consultationId}")
    public ResponseEntity<?> deleteConsultation(@PathVariable Long consultationId) {
        var deleteConsultationCommand = new DeleteConsultationCommand(consultationId);
        commandService.handle(deleteConsultationCommand);
        return ResponseEntity.ok("Consultation deleted successfully");
    }

    @Operation(summary = "Update a consultation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultation updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Consultation not found")
    })
    @PutMapping("/{consultationId}")
    public ResponseEntity<ConsultationResource> updateConsultation(@PathVariable Long consultationId, @RequestBody CreateConsultationResource resource) {
        var updateConsultationCommand = CreateConsultationCommandFromResourceAssembler.toCommandFromResource(resource);
        var updatedConsultation = commandService.handle(updateConsultationCommand);
        if (updatedConsultation.isEmpty()) {
            throw new IllegalArgumentException("Invalid input data for updating consultation");
        }
        var consultationResource = ConsultationResourceFromEntityAssembler.toResourceFromEntity(updatedConsultation.get());
        return new ResponseEntity<>(consultationResource, HttpStatus.OK);
    }

    @Operation(summary = "Get all consultations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultations retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No consultations found")
    })
    @GetMapping
    public ResponseEntity<List<ConsultationResource>> getAllConsultations() {
        var getAllConsultationsQuery= new GetAllConsultationsQuery();
        var consultations = queryService.handle(getAllConsultationsQuery);
        if (consultations.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var consultationResources = consultations.stream()
                .map(ConsultationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(consultationResources);
    }

    @Operation(summary = "Get all consultations by farmer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultations retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No consultations found for the farmer")
    })
    @GetMapping("/farmer")
    public ResponseEntity<List<ConsultationResource>> getAllConsultationsByFarmerId(@AuthenticationPrincipal UserDetails userDetails) {
        var email = userDetails.getUsername();
        var farmerId = externalIamService.fetchUserIdByEmail(email);

        if (farmerId == null) {
            return ResponseEntity.notFound().build();
        }

        var getAllConsultationsQuery = new GetAllConsultationsByFarmerIdQuery(farmerId);
        var consultations = queryService.handle(getAllConsultationsQuery);
        if (consultations.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var consultationResources = consultations.stream()
                .map(ConsultationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(consultationResources);
    }
}
