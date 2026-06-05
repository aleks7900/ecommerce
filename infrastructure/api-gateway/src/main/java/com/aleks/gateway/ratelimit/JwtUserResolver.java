package com.aleks.gateway.ratelimit;

import java.security.Principal;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtUserResolver {

  public Mono<String> resolveUserId(
      ServerWebExchange exchange
  ) {

    return exchange

        .getPrincipal()

        .map(Principal::getName)

        .defaultIfEmpty("anonymous");
  }
}