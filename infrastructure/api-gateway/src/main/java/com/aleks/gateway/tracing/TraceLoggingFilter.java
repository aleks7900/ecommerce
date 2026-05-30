package com.aleks.gateway.tracing;

import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TraceLoggingFilter
    implements GlobalFilter, Ordered {

  private final Tracer tracer;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange,
      GatewayFilterChain chain) {

    return chain.filter(exchange)
        .then(Mono.fromRunnable(() -> {

          if (tracer.currentSpan() == null) {
            return;
          }

          String traceId =
              tracer.currentSpan()
                  .context()
                  .traceId();

          String spanId =
              tracer.currentSpan()
                  .context()
                  .spanId();

          log.info("""
                  
                  Request trace:
                  traceId={}
                  spanId={}
                  method={}
                  path={}
                  status={}
                  
                  """,

              traceId,
              spanId,
              exchange.getRequest().getMethod(),
              exchange.getRequest().getPath(),
              exchange.getResponse().getStatusCode()
          );
        }));
  }

  @Override
  public int getOrder() {
    return -2;
  }
}
