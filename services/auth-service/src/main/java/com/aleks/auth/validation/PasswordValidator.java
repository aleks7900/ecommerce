package com.aleks.auth.validation;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

  public boolean isValid(String password) {

    return password != null
        && password.length() >= 8
        && password.matches(".*[A-Z].*")
        && password.matches(".*[a-z].*")
        && password.matches(".*\\d.*");
  }
}
