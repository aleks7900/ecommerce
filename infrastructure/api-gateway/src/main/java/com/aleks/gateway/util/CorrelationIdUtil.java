package com.aleks.gateway.util;

import org.springframework.web.server.ServerWebExchange;

public class CorrelationIdUtil {

  public static final String HEADER_NAME = "X-Correlation-Id";

  private CorrelationIdUtil() {
  }

  public static String getCorrelationId(ServerWebExchange exchange) {

    return exchange.getRequest()
        .getHeaders()
        .getFirst(HEADER_NAME);
  }
}