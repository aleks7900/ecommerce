package com.aleks.gateway.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BucketFactory {

  public static Bucket createBucket() {

    return Bucket.builder()
        .addLimit(
            Bandwidth.simple(
                RateLimitProperties.CAPACITY,
                RateLimitProperties.REFILL_PERIOD
            )
        )
        .build();
  }
}