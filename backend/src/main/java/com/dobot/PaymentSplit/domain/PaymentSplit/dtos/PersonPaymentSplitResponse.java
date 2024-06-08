package com.dobot.PaymentSplit.domain.PaymentSplit.dtos;

public record PersonPaymentSplitResponse(String name,
        Long orderAmount,
        Long adjustedAmount,
        Long paymentAmount) {

}
