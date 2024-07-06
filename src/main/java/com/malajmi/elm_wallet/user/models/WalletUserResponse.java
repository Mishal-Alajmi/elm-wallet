package com.malajmi.elm_wallet.user.models;

import com.malajmi.elm_wallet.wallet.models.WalletResponse;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.UUID;

@Builder
@ResponseStatus(HttpStatus.OK)
public record WalletUserResponse(UUID userId, String username, String email, WalletResponse wallet, Instant createdAt) {
}
