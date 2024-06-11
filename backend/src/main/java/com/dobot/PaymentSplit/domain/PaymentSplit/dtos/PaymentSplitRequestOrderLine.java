package com.dobot.PaymentSplit.domain.PaymentSplit.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PaymentSplitRequestOrderLine(
                @NotEmpty(message = "이름을 일벽하세요.") String name,
                @NotNull(message = "주문 금액을 입력하세요.") @Min(value = 0, message = "0 이상을 입력하세요.") Long amount) {
}
