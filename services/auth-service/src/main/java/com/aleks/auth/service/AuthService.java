package com.aleks.auth.service;

import com.aleks.auth.dto.request.LoginRequest;
import com.aleks.auth.dto.request.RefreshTokenRequest;
import com.aleks.auth.dto.request.RegisterRequest;
import com.aleks.auth.dto.response.AuthResponse;
import com.aleks.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserServiceImpl userServiceImpl;

  private final JwtService jwtService;

  private final RefreshTokenService refreshTokenService;

  public AuthResponse register(RegisterRequest request) {

    User user = userServiceImpl.createUser(request);

    String accessToken =
        jwtService.generateAccessToken(user);

    String refreshToken =
        refreshTokenService.generate(user);

    return new AuthResponse(
        accessToken,
        refreshToken
    );
  }

  public AuthResponse login(LoginRequest request) {

    User user = userServiceImpl.authenticate(request);

    String accessToken =
        jwtService.generateAccessToken(user);

    String refreshToken =
        refreshTokenService.generate(user);

    return new AuthResponse(
        accessToken,
        refreshToken
    );
  }

  public AuthResponse refresh(
      RefreshTokenRequest request
  ) {

    User user =
        refreshTokenService.validateAndGetUser(
            request.refreshToken()
        );

    String accessToken =
        jwtService.generateAccessToken(user);

    String refreshToken =
        refreshTokenService.rotate(user);

    return new AuthResponse(
        accessToken,
        refreshToken
    );
  }
}
