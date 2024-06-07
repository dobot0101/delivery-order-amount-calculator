package com.dobot.doac.domain.order.dtos;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record OrderRequest(
    @NotEmpty(message = "주문을 입력하세요.") List<PersonOrderRequest> personOrders,
    @NotEmpty(message = "할인 금액을 입력하세요.") @Min(value = 0, message = "0 이상을 입력하세요.") Long discountAmount,
    @NotEmpty(message = "배달비를 입력하세요.") @Min(value = 0, message = "0 이상을 입력하세요.") Long deliveryFee) {

}
