package com.growwithme.iam.domain.services;

import com.growwithme.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
  void handle(SeedRolesCommand command);
}
