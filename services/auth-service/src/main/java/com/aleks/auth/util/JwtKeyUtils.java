package com.aleks.auth.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public final class JwtKeyUtils {

  private JwtKeyUtils() {
  }

  public static KeyPair generateRsaKey()
      throws Exception {

    KeyPairGenerator generator =
        KeyPairGenerator.getInstance("RSA");

    generator.initialize(2048);

    return generator.generateKeyPair();
  }
}