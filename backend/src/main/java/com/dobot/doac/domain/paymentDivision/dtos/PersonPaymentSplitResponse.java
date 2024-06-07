package com.dobot.doac.domain.paymentDivision.dtos;

public record PersonPaymentSplitResponse(String name,
    Long orderAmount,
    Long adjustedAmount,
    Long paymentAmount) {

}
