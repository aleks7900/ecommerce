package com.aleks.analytics.config;

import com.aleks.avro.OrderCreatedEvent;
import com.aleks.avro.ProductCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.util.Utf8;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Utf8ToProductCreatedEventConverter
    implements Converter<Utf8, ProductCreatedEvent> {

  private final ObjectMapper mapper;

  public Utf8ToProductCreatedEventConverter(
      ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public ProductCreatedEvent convert(Utf8 source) {

    try {

      return mapper.readValue(
          source.toString(),
          ProductCreatedEvent.class
      );

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
