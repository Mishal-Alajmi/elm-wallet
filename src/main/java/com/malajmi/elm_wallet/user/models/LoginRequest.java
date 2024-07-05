package com.malajmi.elm_wallet.user.models;

import lombok.NonNull;

import javax.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "username must not be empty")
        String username,
        @NotBlank(message = "password must not be empty")
        String password) {}
