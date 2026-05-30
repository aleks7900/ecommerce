package com.aleks.auth.mapper;

import com.aleks.auth.dto.response.AuthResponse;
import com.aleks.auth.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public AuthResponse toAuthResponse(
      User user,
      String accessToken,
      String refreshToken
  ) {

    return new AuthResponse(
        accessToken,
        refreshToken
    );
  }
}