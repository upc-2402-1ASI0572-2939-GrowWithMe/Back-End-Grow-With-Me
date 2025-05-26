package java.com.growwithme.iam.domain.services;

import java.com.growwithme.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
