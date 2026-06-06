package com.aleks.analytics.kafka;

import com.aleks.analytics.service.AnalyticsService;
import com.aleks.avro.ProductCreatedEvent;
import com.aleks.outbox.dto.DebeziumMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCreatedConsumer {

  private final ObjectMapper objectMapper;

  private final AnalyticsService analyticsService;

  @KafkaListener(
      topics = "product-created",
      groupId = "analytics-group"
  )
  public void consume(
      String message
  ) throws Exception {

    DebeziumMessage wrapper =
        objectMapper.readValue(
            message,
            DebeziumMessage.class
        );

    String json = wrapper.getPayload();

    Decoder decoder =
        DecoderFactory.get().jsonDecoder(
            ProductCreatedEvent.getClassSchema(),
            json
        );

    SpecificDatumReader<ProductCreatedEvent> reader =
        new SpecificDatumReader<>(
            ProductCreatedEvent.class
        );

    ProductCreatedEvent event =
        reader.read(null, decoder);

    analyticsService.productCreated();
  }
}