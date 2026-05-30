package com.aleks.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class CorrelationIdFilter implements GlobalFilter {

  public static final String HEADER_NAME = "X-Correlation-Id";

  @Override
  public Mono<Void> filter(ServerWebExchange exchange,
      GatewayFilterChain chain) {

    String correlationId = exchange.getRequest()
        .getHeaders()
        .getFirst(HEADER_NAME);

    if (correlationId == null) {
      correlationId = UUID.randomUUID().toString();
    }

    ServerHttpRequest mutatedRequest = exchange.getRequest()
        .mutate()
        .header(HEADER_NAME, correlationId)
        .build();

    log.info("Request {} {}", mutatedRequest.getMethod(),
        mutatedRequest.getURI());

    return chain.filter(
        exchange.mutate()
            .request(mutatedRequest)
            .build()
    );
  }
}
