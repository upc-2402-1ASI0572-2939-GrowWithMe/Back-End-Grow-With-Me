package com.growwithme.iam.domain.model.queries;

import com.growwithme.iam.domain.model.valueobjects.Roles;

public record GetAllUsersByRoleQuery(Roles role) {
}
