package com.dobot.PaymentSplit.domain.PaymentSplit.dtos;

import java.util.List;

public record PaymentSplitResult(Long deliveryFee,
                Long discountAmount,
                Long totalOrderAmount,
                List<PaymentSplitResultOrderLine> paymentSplitResultOrderLines) {

}
