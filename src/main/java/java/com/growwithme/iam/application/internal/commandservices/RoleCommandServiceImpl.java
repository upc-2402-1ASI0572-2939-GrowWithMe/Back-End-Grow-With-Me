package java.com.growwithme.iam.application.internal.commandservices;

import org.springframework.stereotype.Service;

import java.com.growwithme.iam.domain.model.commands.SeedRolesCommand;
import java.com.growwithme.iam.domain.model.entities.Role;
import java.com.growwithme.iam.domain.model.valueobjects.Roles;
import java.com.growwithme.iam.domain.services.RoleCommandService;
import java.com.growwithme.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import java.util.Arrays;

@Service
public class RoleCommandServiceImpl implements RoleCommandService {
    private final RoleRepository roleRepository;

    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void handle(SeedRolesCommand command) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if (!roleRepository.existsByName(role)) {
                roleRepository.save(new Role(Roles.valueOf(role.name())));
            }
        });
    }
}
