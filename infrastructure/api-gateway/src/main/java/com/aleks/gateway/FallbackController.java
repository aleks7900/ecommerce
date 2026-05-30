package com.aleks.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

  @GetMapping("/orders")
  public Mono<String> ordersFallback() {
    return Mono.just("Order service unavailable");
  }
}
