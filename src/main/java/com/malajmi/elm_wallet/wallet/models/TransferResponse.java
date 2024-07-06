package com.malajmi.elm_wallet.wallet.models;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@Builder
@ResponseStatus(HttpStatus.OK)
public record TransferResponse(boolean success, String message, BigDecimal amount) {
}
