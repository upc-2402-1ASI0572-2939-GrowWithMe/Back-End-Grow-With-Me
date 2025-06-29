package com.growwithme.iam.infrastructure.tokens.jwt;

import com.growwithme.iam.application.internal.outboundservices.tokens.TokenService;
import com.growwithme.iam.infrastructure.tokens.jwt.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface BearerTokenService extends TokenService {

  String getBearerTokenFrom(HttpServletRequest token);

  String generateToken(Authentication authentication);
}
