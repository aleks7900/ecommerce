package com.aleks.analyticsStreams.topology;

import com.aleks.avro.OrderCreatedEvent;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SalesAnalyticsTopology {

  @Bean
  public KStream<String, String> salesStream(
      StreamsBuilder builder
  ) {

    KStream<String, String> orders =

        builder.stream(
            "order-created",
            Consumed.with(
                Serdes.String(),
                Serdes.String()
            )
        );

    orders

        .groupByKey()

        .count()

        .toStream()

        .mapValues(
            Object::toString
        )

        .to(
            "analytics-sales"
        );

    return orders;
  }

  @Bean
  public KStream<String, OrderCreatedEvent>
  topProductsStream(
      StreamsBuilder builder
  ) {

    KStream<String, OrderCreatedEvent> orders =

        builder.stream(
            "order-created"
        );

    orders

        .groupBy(
            (key, event) ->
                event.getProductId()
        )

        .count(
            Materialized.as(
                "top-products-store"
            )
        )

        .toStream()

        .peek(
            (productId, count) ->
                log.info(
                    "Product {} ordered {} times",
                    productId,
                    count
                )
        )

        .mapValues(
            String::valueOf
        )

        .to(
            "analytics-top-products",
            Produced.with(
                Serdes.String(),
                Serdes.String()
            )
        );

    return orders;
  }

  @Bean
  public KStream<String, OrderCreatedEvent>
  ordersWindowStream(
      StreamsBuilder builder
  ) {

    KStream<String, OrderCreatedEvent> orders =

        builder.stream(
            "order-created"
        );

    orders

        .groupByKey()

        .windowedBy(

            TimeWindows.ofSizeWithNoGrace(
                Duration.ofMinutes(5)
            )
        )

        .count()

        .toStream()

        .peek(
            (window, count) ->
                log.info(
                    "Window {} count={}",
                    window,
                    count
                )
        );

    return orders;
  }
}
