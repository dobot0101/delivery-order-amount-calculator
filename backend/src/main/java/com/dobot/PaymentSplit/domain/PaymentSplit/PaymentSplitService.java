package com.dobot.PaymentSplit.domain.PaymentSplit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dobot.PaymentSplit.domain.PaymentSplit.dtos.PaymentSplitRequest;
import com.dobot.PaymentSplit.domain.PaymentSplit.dtos.PaymentSplitResponse;
import com.dobot.PaymentSplit.domain.PaymentSplit.dtos.PaymentSplitRequestOrderLine;
import com.dobot.PaymentSplit.domain.PaymentSplit.dtos.PaymentSplitResponseOrderLine;
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
  public PaymentSplitResponse splitAndChargePayment(PaymentSplitRequest paymentSplitRequest) {
    Long deliveryFee = paymentSplitRequest.deliveryFee();
    Long discountAmount = paymentSplitRequest.discountAmount();

    Long adjustmentAmount = (deliveryFee - discountAmount) / paymentSplitRequest.orderLines().size();

    Order order = Order.builder().id(UUID.randomUUID()).deliveryFee(deliveryFee).discountAmount(discountAmount).createdAt(LocalDateTime.now())
        .build();
    List<OrderLine> orderLines = new ArrayList<>();
    for (PaymentSplitRequestOrderLine personOrderRequest : paymentSplitRequest.orderLines()) {
      Long paymentAmount = personOrderRequest.amount() + adjustmentAmount;
      OrderLine orderLine = OrderLine.builder().id(UUID.randomUUID())
          .name(personOrderRequest.name())
          .orderAmount(personOrderRequest.amount()).adjustmentAmount(adjustmentAmount).paymentAmount(paymentAmount)
          .order(order).createdAt(LocalDateTime.now()).build();
      orderLines
          .add(orderLine);
    }
    order.addOrderLines(orderLines);

    this.orderRepository.save(order);

    List<PaymentSplitResponseOrderLine> PersonPaymentSplitResponse = orderLines.stream()
        .map(po -> new PaymentSplitResponseOrderLine(po.getName(), po.getOrderAmount(), adjustmentAmount,
            po.getPaymentAmount()))
        .toList();
    return new PaymentSplitResponse(deliveryFee, discountAmount, order.getTotalOrderAmount(), PersonPaymentSplitResponse);
  }

}
