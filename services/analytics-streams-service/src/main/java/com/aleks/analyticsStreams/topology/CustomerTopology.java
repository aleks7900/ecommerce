package com.aleks.analyticsStreams.topology;

import com.aleks.avro.OrderCreatedEvent;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerTopology {

  @Bean
  public KStream<String, OrderCreatedEvent>
  customerAnalytics(
      StreamsBuilder builder
  ) {

    KStream<String, OrderCreatedEvent> orders =
        builder.stream(
            "order-created"
        );

    orders

        .groupBy(
            (key, event) ->
                event.getBuyerId()
        )

        .count()

        .toStream()

        .to(
            "analytics-customers"
        );

    return orders;
  }

}
