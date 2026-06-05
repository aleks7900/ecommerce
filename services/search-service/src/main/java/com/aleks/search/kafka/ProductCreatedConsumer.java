package com.aleks.search.kafka;

import com.aleks.avro.ProductCreatedEvent;
import com.aleks.search.document.ProductDocument;
import com.aleks.search.service.SearchService;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCreatedConsumer {

  private final SearchService service;

  @KafkaListener(
      topics = "product-created",
      groupId = "search-group"
  )
  public void consume(
      ProductCreatedEvent event
  ) {

    service.save(
        ProductDocument.builder()
            .id(UUID.fromString(event.getProductId()))
            .name(event.getName())
            .price(BigDecimal.valueOf(event.getPrice()))
            .sellerId(UUID.fromString(event.getSellerId()))
            .build()
    );
  }
}