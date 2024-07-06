package com.malajmi.elm_wallet.wallet.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AddFundsRequest(
        @JsonAlias("user_id")
        Long userId,
        BigDecimal amount,
        @JsonAlias("payment_method")
        String PaymentMethod) {
}
