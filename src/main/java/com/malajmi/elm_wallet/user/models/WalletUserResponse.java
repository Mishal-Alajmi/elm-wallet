package com.malajmi.elm_wallet.user.models;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record WalletUserResponse(UUID userId, String username, String email, Instant createdAt) {
}
