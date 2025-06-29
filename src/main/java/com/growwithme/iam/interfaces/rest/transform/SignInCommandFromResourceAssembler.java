package com.growwithme.iam.interfaces.rest.transform;

import com.growwithme.iam.domain.model.commands.SignInCommand;
import com.growwithme.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {

  public static SignInCommand toCommandFromResource(SignInResource signInResource) {
    return new SignInCommand(signInResource.email(), signInResource.password());
  }
}
