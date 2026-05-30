package com.aleks.auth.security;

import com.aleks.auth.entity.User;
import com.aleks.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
    implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(
      String email
  ) throws UsernameNotFoundException {

    User user =
        userRepository.findByEmail(email)

            .orElseThrow(() ->
                new UsernameNotFoundException(
                    "User not found"
                )
            );

    return org.springframework.security.core.userdetails.User

        .withUsername(user.getEmail())

        .password(user.getPassword())

        .authorities(
            user.getRoles()
                .stream()
                .map(role ->
                    new SimpleGrantedAuthority(
                        "ROLE_" + role.name()
                    )
                )
                .toList()
        )

        .disabled(!user.isEnabled())

        .build();
  }
}