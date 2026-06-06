package com.aleks.auth.service;

import com.aleks.auth.dto.request.ChangePasswordRequest;
import com.aleks.auth.dto.request.UpdateProfileRequest;
import com.aleks.auth.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

  User findByEmail(
      String email
  );

  @Transactional(readOnly = true)
  User findById(
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
