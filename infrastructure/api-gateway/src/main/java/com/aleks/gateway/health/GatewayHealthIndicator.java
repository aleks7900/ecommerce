package com.aleks.gateway.health;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GatewayHealthIndicator implements ReactiveHealthIndicator {

  private final ReactiveStringRedisTemplate redisTemplate;

  @Override
  public Mono<Health> health() {

    return redisTemplate
        .opsForValue()
        .get("health-check")

        .map(value -> Health.up()
            .withDetail("redis", "available")
            .build())

        .onErrorResume(ex ->
            Mono.just(
                Health.down(ex)
                    .withDetail("redis", "unavailable")
                    .build()
            )
        );
  }
}
