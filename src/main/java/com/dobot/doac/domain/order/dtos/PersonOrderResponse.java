package com.dobot.doac.domain.order.dtos;

public record PersonOrderResponse(String name,
    Long orderAmount,
    Long adjustedAmount,
    Long paymentAmount) {

}
