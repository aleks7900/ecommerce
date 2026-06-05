package com.aleks.gateway.ratelimit;

import io.github.bucket4j.Bucket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class RateLimitService {

  private final Map<String, Bucket> buckets =
      new ConcurrentHashMap<>();

  public Bucket resolveBucket(
      String userId
  ) {

    return buckets.computeIfAbsent(
        userId,
        id -> BucketFactory.createBucket()
    );
  }
}