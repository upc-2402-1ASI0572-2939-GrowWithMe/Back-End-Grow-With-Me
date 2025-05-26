package java.com.growwithme.profiles.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.com.growwithme.profiles.domain.model.queries.consultant.GetAllConsultantUsersQuery;
import java.com.growwithme.profiles.domain.model.queries.consultant.GetConsultantUserByIdQuery;
import java.com.growwithme.profiles.domain.services.ConsultantUserCommandService;
import java.com.growwithme.profiles.domain.services.ConsultantUserQueryService;
import java.com.growwithme.profiles.interfaces.rest.resources.consultant.ConsultantUserResource;
import java.com.growwithme.profiles.interfaces.rest.resources.consultant.CreateConsultantUserResource;
import java.com.growwithme.profiles.interfaces.rest.transform.consultant.ConsultantUserResourceFromEntityAssembler;
import java.com.growwithme.profiles.interfaces.rest.transform.consultant.CreateConsultantUserCommandFromResourceAssembler;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/consultants", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Consultants", description = "Consultant Users Management Endpoint")
public class ConsultantUsersController {
    private final ConsultantUserCommandService consultantUserCommandService;
    private final ConsultantUserQueryService consultantUserQueryService;

    @PostMapping
    public ResponseEntity<ConsultantUserResource> createConsultantUser(@RequestBody CreateConsultantUserResource resource) {
        var createConsultantUserCommand = CreateConsultantUserCommandFromResourceAssembler.toCommandFromResource(resource);
        var consultantUser = consultantUserCommandService.handle(createConsultantUserCommand);
        if (consultantUser.isEmpty()) { return ResponseEntity.badRequest().build(); }
        var consultantUserResource = ConsultantUserResourceFromEntityAssembler.toResourceFromEntity(consultantUser.get());
        return new ResponseEntity<>(consultantUserResource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultantUserResource> getConsultantUserById(@PathVariable Long id) {
        var getConsultantUserByIdQuery = new GetConsultantUserByIdQuery(id);
        var consultantUser = consultantUserQueryService.handle(getConsultantUserByIdQuery);
        if (consultantUser.isEmpty()) { return ResponseEntity.notFound().build(); }
        var consultantUserResource = ConsultantUserResourceFromEntityAssembler.toResourceFromEntity(consultantUser.get());
        return ResponseEntity.ok(consultantUserResource);
    }

    @GetMapping()
    public ResponseEntity<List<ConsultantUserResource>> getAllConsultantUsers() {
        var getAllConsultantUsersQuery = new GetAllConsultantUsersQuery();
        var consultantUsers = consultantUserQueryService.handle(getAllConsultantUsersQuery);
        if (consultantUsers.isEmpty()) { return ResponseEntity.notFound().build(); }
        var consultantUserResources = consultantUsers.stream()
                .map(ConsultantUserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(consultantUserResources);
    }
}
