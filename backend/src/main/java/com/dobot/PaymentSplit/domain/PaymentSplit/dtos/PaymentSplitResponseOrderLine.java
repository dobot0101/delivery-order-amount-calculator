package com.dobot.PaymentSplit.domain.PaymentSplit.dtos;

public record PaymentSplitResponseOrderLine(String name,
        Long orderAmount,
        Long adjustedAmount,
        Long paymentAmount) {

}
