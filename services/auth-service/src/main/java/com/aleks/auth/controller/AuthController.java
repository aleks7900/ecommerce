package com.aleks.auth.controller;

import com.aleks.auth.dto.request.ChangePasswordRequest;
import com.aleks.auth.dto.request.LoginRequest;
import com.aleks.auth.dto.request.RefreshTokenRequest;
import com.aleks.auth.dto.request.RegisterRequest;
import com.aleks.auth.dto.request.UpdateProfileRequest;
import com.aleks.auth.dto.response.AuthResponse;
import com.aleks.auth.dto.response.CurrentUserResponse;
import com.aleks.auth.entity.Role;
import com.aleks.auth.entity.User;
import com.aleks.auth.service.AuthService;
import com.aleks.auth.service.UserServiceImpl;
import jakarta.validation.Valid;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private static final Logger log = LoggerFactory.getLogger(AuthController.class);
  private final AuthService authService;

  private final UserServiceImpl userServiceImpl;

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

  @GetMapping("/me")
  public CurrentUserResponse me(
      Authentication authentication
  ) {

    log.info(authentication.getName());

    User user =
        userServiceImpl.findById(
            authentication.getName()
        );

    return new CurrentUserResponse(

        user.getId(),

        user.getEmail(),

        user.getFirstName(),

        user.getLastName(),

        user.getRoles()
            .stream()
            .map(Role::name)
            .collect(Collectors.toSet()),

        user.getCreatedAt()
    );
  }

  @PutMapping("/me")
  public void updateProfile(

      @RequestBody
      UpdateProfileRequest request,

      Authentication authentication
  ) {

    userServiceImpl.updateProfile(
        authentication.getName(),
        request
    );
  }

  @PostMapping("/change-password")
  public void changePassword(

      @RequestBody
      ChangePasswordRequest request,

      Authentication authentication
  ) {

    userServiceImpl.changePassword(
        authentication.getName(),
        request
    );
  }
}
