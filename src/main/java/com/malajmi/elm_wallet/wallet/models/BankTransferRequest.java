package com.malajmi.elm_wallet.wallet.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record BankTransferRequest(
        @JsonAlias("user_id")
        @NotBlank(message = "user_id must not be blank")
        Long userId,
        @JsonAlias("bank_account")
        @NotBlank(message = "bank_account must not be blank")
        String bankAccount,
        @NotBlank(message = "amount must not be empty")
        BigDecimal amount) {
}
