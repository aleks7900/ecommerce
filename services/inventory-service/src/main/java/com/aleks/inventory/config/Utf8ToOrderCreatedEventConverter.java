package com.aleks.inventory.config;

import com.aleks.avro.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.util.Utf8;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Utf8ToOrderCreatedEventConverter
    implements Converter<Utf8, OrderCreatedEvent> {

  private final ObjectMapper mapper;

  public Utf8ToOrderCreatedEventConverter(
      ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public OrderCreatedEvent convert(Utf8 source) {

    try {

      return mapper.readValue(
          source.toString(),
          OrderCreatedEvent.class
      );

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
