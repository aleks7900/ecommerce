package com.aleks.search.kafka;

import com.aleks.search.document.ProductDocument;
import com.aleks.search.service.SearchService;
import com.aleks.shared.event.ProductCreatedEvent;
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
            .id(event.productId())
            .name(event.name())
            .price(event.price())
            .sellerId(event.sellerId())
            .build()
    );
  }
}