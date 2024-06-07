package com.dobot.doac.domain.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.doac.domain.order.dtos.OrderRequest;
import com.dobot.doac.domain.order.dtos.OrderResponse;
import com.dobot.doac.domain.order.entities.Order;

import jakarta.validation.Valid;

@RestController
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/order")
  public ResponseEntity<OrderResponse> splitAndChargePayment(@RequestBody @Valid OrderRequest orderRequest) {
    OrderResponse orderResponse = this.orderService.splitAndChargePayment(orderRequest);
    return ResponseEntity.ok(orderResponse);
  }
}
