package com.dobot.doac.domain.paymentDivision.dtos;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PaymentSplitRequest(
    @NotEmpty(message = "주문을 입력하세요.") List<PersonPaymentSplitRequest> personOrders,
    @NotNull(message = "할인 금액을 입력하세요.") @Min(value = 0, message = "0 이상을 입력하세요.") Long discountAmount,
    @NotNull(message = "배달비를 입력하세요.") @Min(value = 0, message = "0 이상을 입력하세요.") Long deliveryFee) {

}
