package com.aleks.auth.constants;

public final class SecurityConstants {

  private SecurityConstants() {
  }

  public static final long ACCESS_TOKEN_EXPIRATION =
      3600;

  public static final long REFRESH_TOKEN_EXPIRATION =
      604800;

  public static final String TOKEN_ISSUER =
      "auth-service";

  public static final String ROLES_CLAIM =
      "roles";
}