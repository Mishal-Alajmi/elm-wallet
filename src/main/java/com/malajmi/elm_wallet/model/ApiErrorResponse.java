package com.malajmi.elm_wallet.model;

public record ApiErrorResponse(
        int errorCode,
        String description) {
}
