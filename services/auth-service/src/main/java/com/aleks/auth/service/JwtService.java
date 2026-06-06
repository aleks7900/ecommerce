package com.aleks.auth.service;

import com.aleks.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final JwtEncoder jwtEncoder;

  public String generateAccessToken(User user) {

    Instant now = Instant.now();

    JwtClaimsSet claims =
        JwtClaimsSet.builder()

            .issuer("http://localhost:8081")

            .subject(user.getId().toString())

            .claim("email", user.getEmail())

            .claim(
                "roles",
                user.getRoles()
            )

            .issuedAt(now)

            .expiresAt(now.plusSeconds(3600))

            .build();

    return jwtEncoder.encode(
        JwtEncoderParameters.from(claims)
    ).getTokenValue();
  }
}