package com.growwithme.iam.application.internal.commandservices;

import com.growwithme.iam.domain.model.commands.SeedRolesCommand;
import com.growwithme.iam.domain.model.entities.Role;
import com.growwithme.iam.domain.model.valueobjects.Roles;
import com.growwithme.iam.domain.services.RoleCommandService;
import com.growwithme.iam.infrastructure.persistance.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleCommandServiceImpl implements RoleCommandService {

  private final RoleRepository roleRepository;

  public RoleCommandServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public void handle(SeedRolesCommand command) {
    Arrays.stream(Roles.values())
        .forEach(role -> {
          if(!roleRepository.existsByName(role)) {
            roleRepository.save(new Role(Roles.valueOf(role.name())));
          }
        } );
  }
}
