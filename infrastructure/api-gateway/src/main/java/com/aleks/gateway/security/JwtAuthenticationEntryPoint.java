package com.aleks.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint
    implements ServerAuthenticationEntryPoint {

  @Override
  public Mono<Void> commence(ServerWebExchange exchange,
      AuthenticationException ex) {

    log.error("Unauthorized request", ex);

    exchange.getResponse()
        .setStatusCode(HttpStatus.UNAUTHORIZED);

    exchange.getResponse()
        .getHeaders()
        .setContentType(MediaType.APPLICATION_JSON);

    String body = """
                {
                  "error": "Unauthorized"
                }
                """;

    return exchange.getResponse()
        .writeWith(
            Mono.just(
                exchange.getResponse()
                    .bufferFactory()
                    .wrap(body.getBytes())
            )
        );
  }
}
