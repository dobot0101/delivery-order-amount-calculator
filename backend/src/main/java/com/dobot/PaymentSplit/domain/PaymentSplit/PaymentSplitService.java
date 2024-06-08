package com.dobot.PaymentSplit.domain.PaymentSplit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dobot.PaymentSplit.domain.PaymentSplit.dtos.PaymentSplitRequest;
import com.dobot.PaymentSplit.domain.PaymentSplit.dtos.PaymentSplitResponse;
import com.dobot.PaymentSplit.domain.PaymentSplit.dtos.PersonPaymentSplitRequest;
import com.dobot.PaymentSplit.domain.PaymentSplit.dtos.PersonPaymentSplitResponse;
import com.dobot.PaymentSplit.domain.PaymentSplit.entities.Order;
import com.dobot.PaymentSplit.domain.PaymentSplit.entities.OrderLine;

import jakarta.transaction.Transactional;

@Service
public class PaymentSplitService {
  private final OrderRepository orderRepository;

  public PaymentSplitService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Transactional
  public PaymentSplitResponse splitAndChargePayment(PaymentSplitRequest orderRequest) {
    Long deliveryFee = orderRequest.deliveryFee();
    Long discountAmount = orderRequest.discountAmount();

    Long adjustmentAmount = (deliveryFee - discountAmount) / orderRequest.personOrders().size();

    Order order = Order.builder().id(UUID.randomUUID()).deliveryFee(deliveryFee).discountAmount(discountAmount).createdAt(LocalDateTime.now())
        .build();
    List<OrderLine> personOrders = new ArrayList<>();
    for (PersonPaymentSplitRequest personOrderRequest : orderRequest.personOrders()) {
      Long paymentAmount = personOrderRequest.amount() + adjustmentAmount;
      OrderLine personOrder = OrderLine.builder().id(UUID.randomUUID())
          .name(personOrderRequest.name())
          .orderAmount(personOrderRequest.amount()).adjustmentAmount(adjustmentAmount).paymentAmount(paymentAmount)
          .order(order).createdAt(LocalDateTime.now()).build();
      personOrders
          .add(personOrder);
    }
    order.addPersonOrders(personOrders);

    this.orderRepository.save(order);

    List<PersonPaymentSplitResponse> personOrderResponses = personOrders.stream()
        .map(po -> new PersonPaymentSplitResponse(po.getName(), po.getOrderAmount(), adjustmentAmount,
            po.getPaymentAmount()))
        .toList();
    return new PaymentSplitResponse(deliveryFee, discountAmount, order.getTotalOrderAmount(), personOrderResponses);
  }

}
