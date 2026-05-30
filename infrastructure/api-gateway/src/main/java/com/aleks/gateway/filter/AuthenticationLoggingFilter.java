package com.aleks.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthenticationLoggingFilter
    implements GlobalFilter {

  @Override
  public Mono<Void> filter(
      ServerWebExchange exchange,
      GatewayFilterChain chain
  ) {

    return exchange.getPrincipal()
        .cast(Authentication.class)
        .doOnNext(auth ->
            log.info(
                "Authenticated user: {}",
                auth.getName()
            )
        )
        .then(chain.filter(exchange));
  }
}