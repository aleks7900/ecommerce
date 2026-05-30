package com.aleks.gateway.tracing;

import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TracingConfig {

  private final Tracer tracer;

  public void logCurrentTrace() {

    if (tracer.currentSpan() != null) {

      log.info(
          "traceId={}, spanId={}",
          tracer.currentSpan().context().traceId(),
          tracer.currentSpan().context().spanId()
      );
    }
  }
}