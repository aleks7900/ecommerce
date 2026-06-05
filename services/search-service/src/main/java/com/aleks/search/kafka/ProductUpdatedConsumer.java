package com.aleks.search.kafka;

import com.aleks.avro.ProductUpdatedEvent;
import com.aleks.search.document.ProductDocument;
import com.aleks.search.service.SearchService;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductUpdatedConsumer {

  private final SearchService service;

  @KafkaListener(
      topics = "product-updated",
      groupId = "search-group"
  )
  public void consume(
      ProductUpdatedEvent event
  ) {

    service.save(
        ProductDocument.builder()
            .id(UUID.fromString(event.getProductId()))
            .name(event.getName())
            .price(BigDecimal.valueOf(event.getPrice()))
            .build()
    );
  }
}