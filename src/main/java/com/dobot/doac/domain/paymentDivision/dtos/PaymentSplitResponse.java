package com.dobot.doac.domain.paymentDivision.dtos;

import java.util.List;

public record PaymentSplitResponse(Long deliveryFee,
    Long discountAmount,
    Long totalOrderAmount,
    List<PersonPaymentSplitResponse> personOrders) {

}
