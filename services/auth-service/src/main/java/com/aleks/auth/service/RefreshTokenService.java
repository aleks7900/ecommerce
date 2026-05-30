package com.aleks.auth.service;

import com.aleks.auth.entity.RefreshToken;
import com.aleks.auth.entity.User;
import com.aleks.auth.exception.TokenExpiredException;
import com.aleks.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;

  public String generate(User user) {

    String token = UUID.randomUUID().toString();

    RefreshToken refreshToken =
        RefreshToken.builder()

            .token(token)

            .user(user)

            .expiresAt(
                Instant.now()
                    .plusSeconds(604800)
            )

            .revoked(false)

            .build();

    refreshTokenRepository.save(refreshToken);

    return token;
  }

  public User validateAndGetUser(String token) {

    RefreshToken refreshToken =
        refreshTokenRepository.findByToken(token)
            .orElseThrow(() ->
                new TokenExpiredException(
                    "Invalid refresh token"
                )
            );

    if (refreshToken.isRevoked()
        || refreshToken.getExpiresAt()
        .isBefore(Instant.now())) {

      throw new TokenExpiredException(
          "Refresh token expired"
      );
    }

    return refreshToken.getUser();
  }

  public String rotate(User user) {

    return generate(user);
  }
}
