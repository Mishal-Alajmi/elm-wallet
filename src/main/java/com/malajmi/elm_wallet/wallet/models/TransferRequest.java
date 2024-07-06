package com.malajmi.elm_wallet.wallet.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;

public record TransferRequest(
        @JsonAlias("sender_id")
        Long senderId,
        @JsonAlias("recipient_id")
        Long recipientId,
        BigDecimal amount) {
}
