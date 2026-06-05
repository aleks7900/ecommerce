package com.aleks.analyticsStreams.topology;

import com.aleks.avro.OrderCreatedEvent;
import com.aleks.avro.PaymentCompletedEvent;
import lombok.Builder;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Configuration
public class CompletedOrdersTopology {

  @Bean
  public KStream<String, CompletedOrderMetric>
  completedOrdersStream(
      StreamsBuilder builder
  ) {

    KStream<String, OrderCreatedEvent> orders =

        builder.stream(
            "order-created"
        );

    KStream<String, PaymentCompletedEvent> payments =

        builder.stream(
            "payment-completed"
        );

    return orders.join(

        payments,

        (order, payment) ->

            CompletedOrderMetric
                .builder()
                .orderId(
                    UUID.fromString(order.getOrderId())
                )
                .productId(
                    UUID.fromString(order.getProductId())
                )
                .amount(
                    BigDecimal.valueOf(payment.getAmount())
                )
                .build(),

        JoinWindows.ofTimeDifferenceWithNoGrace(
            Duration.ofMinutes(30)
        )
    );
  }

  @Builder
  public record CompletedOrderMetric(

      UUID orderId,

      UUID productId,

      BigDecimal amount
  ) {
  }
}