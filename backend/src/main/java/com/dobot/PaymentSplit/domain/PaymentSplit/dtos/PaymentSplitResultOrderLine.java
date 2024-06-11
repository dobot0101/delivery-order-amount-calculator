package com.dobot.PaymentSplit.domain.PaymentSplit.dtos;

public record PaymentSplitResultOrderLine(String name,
                Long orderAmount,
                Long adjustedAmount,
                Long paymentAmount) {

}
