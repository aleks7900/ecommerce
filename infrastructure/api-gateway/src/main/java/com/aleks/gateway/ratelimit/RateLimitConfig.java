package com.aleks.gateway.ratelimit;

import java.security.Principal;
import java.util.Objects;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimitConfig {

  /**
   * Rate limit by authenticated user.
   * Falls back to IP address if user is anonymous.
   */
  @Bean
  public KeyResolver userKeyResolver() {

    return exchange ->
        exchange.getPrincipal()

            .map(Principal::getName)

            .switchIfEmpty(
                Mono.just(
                    Objects.requireNonNull(exchange.getRequest()
                            .getRemoteAddress())
                        .getAddress()
                        .getHostAddress()
                )
            );
  }
}