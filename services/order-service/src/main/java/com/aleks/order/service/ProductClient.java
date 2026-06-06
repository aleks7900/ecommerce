package com.aleks.order.service;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "product-service",
    url = "${product-service.url}",
    configuration = FeignConfig.class
)
public interface ProductClient {

  @GetMapping("/api/products/{id}")
  ProductResponse getById(
      @PathVariable UUID id
  );
}
