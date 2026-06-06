package com.aleks.order.service;

import com.aleks.avro.OrderCreatedEvent;
import com.aleks.order.dto.request.CreateOrderRequest;
import com.aleks.order.entity.Order;
import com.aleks.order.entity.OrderStatus;
import com.aleks.order.exception.OrderNotFoundException;
import com.aleks.order.kafka.OrderEventPublisher;
import com.aleks.order.repository.OrderRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  private final ProductClient productClient;

  private final OrderEventPublisher orderEventPublisher;

  public Order create(
      CreateOrderRequest request
  ) {

    Authentication authentication =
        SecurityContextHolder
            .getContext()
            .getAuthentication();

    String email =
        authentication.getName();

    ProductResponse product =
        productClient.getById(
            request.productId()
        );

    BigDecimal totalPrice =
        product.price()
            .multiply(
                BigDecimal.valueOf(
                    request.quantity()
                )
            );

    Order order = Order.builder()
        .productId(request.productId())
        .buyerId(UUID.fromString(email))
        .quantity(request.quantity())
        .totalPrice(totalPrice)
        .status(OrderStatus.CREATED)
        .build();

    Order savedOrder =
        orderRepository.save(order);

    OrderCreatedEvent event =
        OrderCreatedEvent.newBuilder()
            .setOrderId(
                savedOrder.getId().toString()
            )
            .setProductId(
                savedOrder.getProductId().toString()
            )
            .setBuyerId(
                savedOrder.getBuyerId().toString()
            )
            .setQuantity(
                savedOrder.getQuantity()
            )
            .setTotalPrice(
                savedOrder.getTotalPrice().doubleValue()
            )
            .setCreatedAt(
                Instant.now().toString()
            )
            .build();

    orderEventPublisher.publishOrderCreatedEvent(
        event
    );

    return savedOrder;
  }

  public Order getById(
      UUID id
  ) {

    return orderRepository.findById(id)
        .orElseThrow(() ->
            new OrderNotFoundException(
                "Order not found: " + id
            )
        );
  }

  public List<Order> getAll() {

    return orderRepository.findAll();
  }
}
