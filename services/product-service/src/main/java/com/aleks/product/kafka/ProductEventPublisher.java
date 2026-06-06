package com.aleks.product.kafka;

import static com.aleks.avro.util.AvroJsonUtils.toJson;
import com.aleks.avro.ProductCreatedEvent;
import com.aleks.avro.ProductUpdatedEvent;
import com.aleks.outbox.service.OutboxPublisherService;
import java.io.ByteArrayOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventPublisher {

  private static final String PRODUCT_CREATED_TOPIC =
      "product-created";

  private static final String PRODUCT_UPDATED_TOPIC =
      "product-updated";

  private final OutboxPublisherService outboxPublisherService;

  public void sendProductCreatedEvent(
      ProductCreatedEvent event
  ) {

    log.info(
        "Publishing ProductCreatedEvent for product {}",
        event.getProductId()
    );

    outboxPublisherService.publish(
        "PRODUCT",
        String.valueOf(event.getProductId()),
        PRODUCT_CREATED_TOPIC,
        toJson(event)
    );
  }

  public void sendProductUpdatedEvent(
      ProductUpdatedEvent event
  ) {

    log.info(
        "Publishing ProductUpdatedEvent for product {}",
        event.getProductId()
    );

    outboxPublisherService.publish(
        "PRODUCT",
        String.valueOf(event.getProductId()),
        PRODUCT_UPDATED_TOPIC,
        toJson(event)
    );
  }
}
