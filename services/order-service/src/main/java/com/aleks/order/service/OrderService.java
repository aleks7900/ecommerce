package com.aleks.order.service;

import com.aleks.order.dto.request.CreateOrderRequest;
import com.aleks.order.entity.Order;
import com.aleks.order.entity.OrderStatus;
import com.aleks.order.exception.OrderNotFoundException;
import com.aleks.order.kafka.OrderEventPublisher;
import com.aleks.order.repository.OrderRepository;
import com.aleks.shared.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  private final OrderEventPublisher orderEventPublisher;

  public Order create(
      CreateOrderRequest request
  ) {

    Order order = Order.builder()
        .productId(request.productId())
        .buyerId(request.buyerId())
        .quantity(request.quantity())
        .totalPrice(request.totalPrice())
        .status(OrderStatus.CREATED)
        .build();

    Order savedOrder =
        orderRepository.save(order);

    OrderCreatedEvent event =
        OrderCreatedEvent.builder()
            .orderId(savedOrder.getId())
            .productId(savedOrder.getProductId())
            .buyerId(savedOrder.getBuyerId())
            .quantity(savedOrder.getQuantity())
            .totalPrice(savedOrder.getTotalPrice())
            .createdAt(Instant.now())
            .build();

    orderEventPublisher
        .publishOrderCreatedEvent(event);

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
