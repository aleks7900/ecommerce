package com.aleks.auth.service;

import com.aleks.auth.dto.request.ChangePasswordRequest;
import com.aleks.auth.dto.request.LoginRequest;
import com.aleks.auth.dto.request.RegisterRequest;
import com.aleks.auth.dto.request.UpdateProfileRequest;
import com.aleks.auth.entity.Role;
import com.aleks.auth.entity.User;
import com.aleks.auth.exception.InvalidCredentialsException;
import com.aleks.auth.exception.UserAlreadyExistsException;
import com.aleks.auth.repository.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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

        .roles(Set.of(Role.valueOf("USER")))

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

  @Override
  @Transactional(readOnly = true)
  public User findByEmail(
      String email
  ) {

    return userRepository
        .findByEmail(email)
        .orElseThrow(
            () -> new UsernameNotFoundException(
                email
            )
        );
  }

  @Override
  public void updateProfile(

      String email,

      UpdateProfileRequest request
  ) {

    User user =
        findByEmail(email);

    user.setFirstName(
        request.firstName()
    );

    user.setLastName(
        request.lastName()
    );

    user.setAvatarUrl(
        request.avatarUrl()
    );

    userRepository.save(
        user
    );
  }

  @Override
  public void changePassword(

      String email,

      ChangePasswordRequest request
  ) {

    User user =
        findByEmail(email);

    boolean matches =
        passwordEncoder.matches(

            request.currentPassword(),

            user.getPassword()
        );

    if (!matches) {

      throw new IllegalArgumentException(
          "Current password is incorrect"
      );
    }

    user.setPassword(

        passwordEncoder.encode(

            request.newPassword()
        )
    );

    userRepository.save(
        user
    );
  }
}
