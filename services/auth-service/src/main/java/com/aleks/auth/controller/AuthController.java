package com.aleks.auth.controller;

import com.aleks.auth.dto.request.LoginRequest;
import com.aleks.auth.dto.request.RefreshTokenRequest;
import com.aleks.auth.dto.request.RegisterRequest;
import com.aleks.auth.dto.response.AuthResponse;
import com.aleks.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public AuthResponse register(
      @RequestBody @Valid RegisterRequest request
  ) {

    return authService.register(request);
  }

  @PostMapping("/login")
  public AuthResponse login(
      @RequestBody @Valid LoginRequest request
  ) {

    return authService.login(request);
  }

  @PostMapping("/refresh")
  public AuthResponse refresh(
      @RequestBody @Valid RefreshTokenRequest request
  ) {

    return authService.refresh(request);
  }
}
