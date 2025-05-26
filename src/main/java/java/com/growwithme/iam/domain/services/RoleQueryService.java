package java.com.growwithme.iam.domain.services;

import java.com.growwithme.iam.domain.model.entities.Role;
import java.com.growwithme.iam.domain.model.queries.GetAllRolesQuery;
import java.com.growwithme.iam.domain.model.queries.GetRoleByNameQuery;
import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);
}
