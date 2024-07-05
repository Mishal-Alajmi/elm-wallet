package com.malajmi.elm_wallet.user.models;

import jakarta.annotation.Nullable;

public record UpdateUserRequest(@Nullable String firstName, @Nullable String lastName, @Nullable String email) {
}
