package com.dobot.PaymentSplit.domain.PaymentSplit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.PaymentSplit.domain.PaymentSplit.dtos.PaymentSplitRequest;
import com.dobot.PaymentSplit.domain.PaymentSplit.dtos.PaymentSplitResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentSplitController {
  private final PaymentSplitService paymentSplitService;

  @PostMapping("/split-payment")
  public ResponseEntity<PaymentSplitResult> splitAndChargePayment(
      @RequestBody @Valid PaymentSplitRequest paymentSplitRequest) {
    PaymentSplitResult paymentSplitResult = this.paymentSplitService.splitAndChargePayment(paymentSplitRequest);
    return ResponseEntity.ok(paymentSplitResult);
  }
}
