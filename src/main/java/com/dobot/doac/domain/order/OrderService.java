package com.dobot.doac.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dobot.doac.domain.order.dtos.OrderRequest;
import com.dobot.doac.domain.order.dtos.OrderResponse;
import com.dobot.doac.domain.order.dtos.PersonOrderRequest;
import com.dobot.doac.domain.order.dtos.PersonOrderResponse;
import com.dobot.doac.domain.order.entities.Order;
import com.dobot.doac.domain.order.entities.PersonOrder;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Transactional
  public OrderResponse splitAndChargePayment(OrderRequest orderRequest) {
    Long deliveryFee = orderRequest.deliveryFee();
    Long discountAmount = orderRequest.discountAmount();
    Long adjustedFee = (deliveryFee - discountAmount) / orderRequest.personOrders().size();

    Order order = new Order(UUID.randomUUID(), deliveryFee, discountAmount, List.of());
    List<PersonOrder> personOrders = new ArrayList<>();
    for (PersonOrderRequest personOrder : orderRequest.personOrders()) {
      Long paymentAmount = personOrder.amount() + adjustedFee;
      personOrders
          .add(new PersonOrder(UUID.randomUUID(), personOrder.name(), personOrder.amount(), adjustedFee, paymentAmount,
              order));
    }
    order.addPersonOrders(personOrders);

    this.orderRepository.save(order);

    List<PersonOrderResponse> personOrderResponses = personOrders.stream()
        .map(po -> new PersonOrderResponse(po.getName(), po.getOrderAmount(), adjustedFee, po.getPaymentAmount()))
        .toList();
    return new OrderResponse(deliveryFee, discountAmount, order.getTotalOrderAmount(), personOrderResponses);
  }

}
