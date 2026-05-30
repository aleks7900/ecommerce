package com.aleks.gateway.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Order(-2)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

  @Override
  public Mono<Void> handle(ServerWebExchange exchange,
      Throwable ex) {

    log.error("Gateway error", ex);

    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);

    exchange.getResponse()
        .getHeaders()
        .setContentType(MediaType.APPLICATION_JSON);

    String body = """
        {
          "message": "Internal gateway error"
        }
        """;

    return exchange.getResponse()
        .writeWith(Mono.just(
            exchange.getResponse()
                .bufferFactory()
                .wrap(body.getBytes())
        ));
  }
}
