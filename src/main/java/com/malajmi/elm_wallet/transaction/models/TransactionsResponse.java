package com.malajmi.elm_wallet.transaction.models;

import com.malajmi.elm_wallet.transaction.enums.TransactionStatus;
import com.malajmi.elm_wallet.transaction.enums.TransactionType;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
@ResponseStatus(HttpStatus.OK)
public record TransactionsResponse(UUID transactionId,
                                   TransactionType type,
                                   BigDecimal amount,
                                   Instant date,
                                   TransactionStatus status) {}
