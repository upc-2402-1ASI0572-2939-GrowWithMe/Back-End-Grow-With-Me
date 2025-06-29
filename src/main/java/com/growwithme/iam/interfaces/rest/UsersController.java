package com.growwithme.iam.interfaces.rest;

import com.growwithme.iam.domain.model.entities.Role;
import com.growwithme.iam.domain.model.queries.GetAllUsersByRoleQuery;
import com.growwithme.iam.domain.model.valueobjects.Roles;
import com.growwithme.iam.domain.services.UserQueryService;
import com.growwithme.iam.interfaces.rest.resources.UserResource;
import com.growwithme.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "Users Endpoints")
public class UsersController {
    private final UserQueryService userQueryService;

    @GetMapping("/farmers")
    public ResponseEntity<List<UserResource>> getAllFarmers() {
        var users = userQueryService.handle(new GetAllUsersByRoleQuery(Roles.FARMER_ROLE));
        List<UserResource> resources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/consultants")
    public ResponseEntity<List<UserResource>> getAllConsultants() {
        var users = userQueryService.handle(new GetAllUsersByRoleQuery(Roles.CONSULTANT_ROLE));
        List<UserResource> resources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
