package com.aleks.analyticsStreams.topology;

import com.aleks.shared.event.OrderCreatedEvent;
import com.aleks.shared.event.PaymentCompletedEvent;
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
                    order.orderId()
                )
                .productId(
                    order.productId()
                )
                .amount(
                    payment.amount()
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