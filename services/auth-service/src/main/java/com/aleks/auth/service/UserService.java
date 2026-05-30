package com.aleks.auth.service;

import com.aleks.auth.dto.request.LoginRequest;
import com.aleks.auth.dto.request.RegisterRequest;
import com.aleks.auth.entity.Role;
import com.aleks.auth.entity.User;
import com.aleks.auth.exception.InvalidCredentialsException;
import com.aleks.auth.exception.UserAlreadyExistsException;
import com.aleks.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public User createUser(RegisterRequest request) {

    if (userRepository.existsByEmail(request.email())) {

      throw new UserAlreadyExistsException(
          "User already exists"
      );
    }

    User user = User.builder()

        .id(UUID.randomUUID())

        .email(request.email())

        .password(
            passwordEncoder.encode(
                request.password()
            )
        )

        .roles(List.of(Role.valueOf("USER")))

        .enabled(true)

        .build();

    return userRepository.save(user);
  }

  public User authenticate(LoginRequest request) {

    User user =
        userRepository.findByEmail(request.email())
            .orElseThrow(() ->
                new InvalidCredentialsException(
                    "Invalid credentials"
                )
            );

    boolean matches =
        passwordEncoder.matches(
            request.password(),
            user.getPassword()
        );

    if (!matches) {

      throw new InvalidCredentialsException(
          "Invalid credentials"
      );
    }

    return user;
  }
}
