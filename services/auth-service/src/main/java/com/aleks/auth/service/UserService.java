package com.aleks.auth.service;

import com.aleks.auth.dto.request.ChangePasswordRequest;
import com.aleks.auth.dto.request.UpdateProfileRequest;
import com.aleks.auth.entity.User;

public interface UserService {

  User findByEmail(
      String email
  );

  void updateProfile(
      String email,
      UpdateProfileRequest request
  );

  void changePassword(
      String email,
      ChangePasswordRequest request
  );
}
