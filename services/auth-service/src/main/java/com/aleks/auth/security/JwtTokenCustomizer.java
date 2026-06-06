package com.aleks.auth.security;

import com.aleks.auth.entity.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtTokenCustomizer {

  public JwtClaimsSet customize(User user) {

    Instant now = Instant.now();

    return JwtClaimsSet.builder()

        .issuer("http://localhost:8081")

        .subject(user.getId().toString())

        .claim("email", user.getEmail())

        .claim(
            "roles",
            user.getRoles()
                .stream()
                .map(Enum::name)
                .toList()
        )

        .issuedAt(now)

        .expiresAt(
            now.plusSeconds(3600)
        )

        .build();
  }
}