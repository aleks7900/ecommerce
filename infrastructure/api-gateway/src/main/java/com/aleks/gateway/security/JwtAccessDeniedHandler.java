package com.aleks.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtAccessDeniedHandler
    implements ServerAccessDeniedHandler {

  @Override
  public Mono<Void> handle(ServerWebExchange exchange,
      AccessDeniedException denied) {

    log.error("Access denied", denied);

    exchange.getResponse()
        .setStatusCode(HttpStatus.FORBIDDEN);

    exchange.getResponse()
        .getHeaders()
        .setContentType(MediaType.APPLICATION_JSON);

    String body = """
                {
                  "error": "Forbidden"
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
