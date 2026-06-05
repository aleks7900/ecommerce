package com.aleks.gateway.ratelimit;

import java.time.Duration;

public final class RateLimitProperties {

  private RateLimitProperties() {
  }

  public static final long CAPACITY = 100;

  public static final Duration REFILL_PERIOD =
      Duration.ofMinutes(1);
}