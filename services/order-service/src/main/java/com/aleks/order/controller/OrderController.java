package com.aleks.order.controller;

import com.aleks.order.dto.request.CreateOrderRequest;
import com.aleks.order.entity.Order;
import com.aleks.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Order create(
      @RequestBody
      @Valid
      CreateOrderRequest request
  ) {

    return orderService.create(request);
  }

  @GetMapping("/{id}")
  public Order getById(
      @PathVariable UUID id
  ) {

    return orderService.getById(id);
  }

  @GetMapping
  public List<Order> getAll() {

    return orderService.getAll();
  }
}