package com.dobot.doac.domain.paymentDivision;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.doac.domain.paymentDivision.dtos.PaymentSplitRequest;
import com.dobot.doac.domain.paymentDivision.dtos.PaymentSplitResponse;

import jakarta.validation.Valid;

@RestController
public class PaymentSplitController {
  private final PaymentSplitService orderService;

  public PaymentSplitController(PaymentSplitService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/split-payment")
  public ResponseEntity<PaymentSplitResponse> splitAndChargePayment(
      @RequestBody @Valid PaymentSplitRequest orderRequest) {
    PaymentSplitResponse orderResponse = this.orderService.splitAndChargePayment(orderRequest);
    return ResponseEntity.ok(orderResponse);
  }
}
