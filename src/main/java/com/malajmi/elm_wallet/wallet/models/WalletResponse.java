package com.malajmi.elm_wallet.wallet.models;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record WalletResponse(UUID walletId, BigDecimal balance, Instant createdAt) {
}
