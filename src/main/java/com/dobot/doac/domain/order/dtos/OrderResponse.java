package com.dobot.doac.domain.order.dtos;

import java.util.List;

public record OrderResponse(Long deliveryFee,
    Long discountAmount,
    Long totalOrderAmount,
    List<PersonOrderResponse> personOrders) {

}
