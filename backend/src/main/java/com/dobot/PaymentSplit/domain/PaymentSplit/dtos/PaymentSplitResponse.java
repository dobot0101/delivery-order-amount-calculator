package com.dobot.PaymentSplit.domain.PaymentSplit.dtos;

import java.util.List;

public record PaymentSplitResponse(Long deliveryFee,
        Long discountAmount,
        Long totalOrderAmount,
        List<PaymentSplitResponseOrderLine> orderLines) {

}
