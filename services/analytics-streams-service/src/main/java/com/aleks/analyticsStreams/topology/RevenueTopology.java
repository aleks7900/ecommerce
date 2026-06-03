package com.aleks.analyticsStreams.topology;

import com.aleks.shared.event.PaymentCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Slf4j
@Configuration
public class RevenueTopology {

  @Bean
  public KStream<String, PaymentCompletedEvent>
  revenueStream(
      StreamsBuilder builder
  ) {

    KStream<String, PaymentCompletedEvent> payments =

        builder.stream(
            "payment-completed"
        );

    payments

        .groupByKey()

        .aggregate(

            () -> BigDecimal.ZERO,

            (key, payment, total) ->

                total.add(
                    payment.amount()
                )

        )

        .toStream()

        .peek(
            (key, revenue) ->
                log.info(
                    "Revenue = {}",
                    revenue
                )
        );

    return payments;
  }
}