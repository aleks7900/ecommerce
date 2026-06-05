package com.aleks.gateway;

import org.springframework.http.ResponseEntity;
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

  @GetMapping("/products")
  public ResponseEntity<String> fallback() {
    return ResponseEntity.ok(
        "Product service unavailable"
    );
  }
}
