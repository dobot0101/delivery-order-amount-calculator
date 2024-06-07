package com.dobot.doac.domain.paymentDivision;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dobot.doac.domain.paymentDivision.dtos.PaymentSplitRequest;
import com.dobot.doac.domain.paymentDivision.dtos.PaymentSplitResponse;
import com.dobot.doac.domain.paymentDivision.dtos.PersonPaymentSplitRequest;
import com.dobot.doac.domain.paymentDivision.dtos.PersonPaymentSplitResponse;
import com.dobot.doac.domain.paymentDivision.entities.PaymentSplit;
import com.dobot.doac.domain.paymentDivision.entities.PersonPaymentSplit;

import jakarta.transaction.Transactional;

@Service
public class PaymentSplitService {
  private final PaymentSplitRepository orderRepository;

  public PaymentSplitService(PaymentSplitRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Transactional
  public PaymentSplitResponse splitAndChargePayment(PaymentSplitRequest orderRequest) {
    Long deliveryFee = orderRequest.deliveryFee();
    Long discountAmount = orderRequest.discountAmount();

    Long adjustmentAmount = (deliveryFee - discountAmount) / orderRequest.personOrders().size();

    var order = PaymentSplit.builder().id(UUID.randomUUID()).deliveryFee(deliveryFee).discountAmount(discountAmount).build();
    List<PersonPaymentSplit> personOrders = new ArrayList<>();
    for (PersonPaymentSplitRequest personOrderRequest : orderRequest.personOrders()) {
      Long paymentAmount = personOrderRequest.amount() + adjustmentAmount;
      PersonPaymentSplit personOrder = PersonPaymentSplit.builder().id(UUID.randomUUID()).name(personOrderRequest.name())
          .orderAmount(personOrderRequest.amount()).adjustmentAmount(adjustmentAmount).paymentAmount(paymentAmount)
          .order(order).build();
      personOrders
          .add(personOrder);
    }
    order.addPersonOrders(personOrders);
    

    this.orderRepository.save(order);

    List<PersonPaymentSplitResponse> personOrderResponses = personOrders.stream()
        .map(po -> new PersonPaymentSplitResponse(po.getName(), po.getOrderAmount(), adjustmentAmount, po.getPaymentAmount()))
        .toList();
    return new PaymentSplitResponse(deliveryFee, discountAmount, order.getTotalOrderAmount(), personOrderResponses);
  }

}
