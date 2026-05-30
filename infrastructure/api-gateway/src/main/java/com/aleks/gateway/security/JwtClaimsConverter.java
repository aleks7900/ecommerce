package com.aleks.gateway.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtClaimsConverter {

  public String extractUserId(Jwt jwt) {

    return jwt.getSubject();
  }

  public String extractEmail(Jwt jwt) {

    return jwt.getClaimAsString("email");
  }

  public List<String> extractRoles(Jwt jwt) {

    return jwt.getClaimAsStringList("roles");
  }
}