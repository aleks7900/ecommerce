package com.aleks.product.kafka;

import com.aleks.shared.event.ProductCreatedEvent;
import com.aleks.shared.event.ProductUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventProducer {

  private static final String PRODUCT_CREATED_TOPIC =
      "product-created";

  private static final String PRODUCT_UPDATED_TOPIC =
      "product-updated";

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void sendProductCreatedEvent(
      ProductCreatedEvent event
  ) {

    log.info(
        "Publishing ProductCreatedEvent for product {}",
        event.productId()
    );

    kafkaTemplate.send(
        PRODUCT_CREATED_TOPIC,
        event.productId().toString(),
        event
    );
  }

  public void sendProductUpdatedEvent(
      ProductUpdatedEvent event
  ) {

    log.info(
        "Publishing ProductUpdatedEvent for product {}",
        event.productId()
    );

    kafkaTemplate.send(
        PRODUCT_UPDATED_TOPIC,
        event.productId().toString(),
        event
    );
  }
}
