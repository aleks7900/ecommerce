package com.aleks.gateway.filter;

import com.aleks.gateway.ratelimit.JwtUserResolver;
import com.aleks.gateway.ratelimit.RateLimitService;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitFilter
    implements GlobalFilter, Ordered {

  private final RateLimitService rateLimitService;

  private final JwtUserResolver userResolver;

  @Override
  public Mono<Void> filter(

      ServerWebExchange exchange,

      GatewayFilterChain chain
  ) {

    return userResolver

        .resolveUserId(exchange)

        .flatMap(userId -> {

          Bucket bucket =

              rateLimitService.resolveBucket(
                  userId
              );

          if (!bucket.tryConsume(1)) {

            log.warn(
                "Rate limit exceeded. user={}",
                userId
            );

            exchange
                .getResponse()
                .setStatusCode(
                    HttpStatus.TOO_MANY_REQUESTS
                );

            return exchange
                .getResponse()
                .setComplete();
          }

          return chain.filter(exchange);
        });
  }

  @Override
  public int getOrder() {

    return -100;
  }
}