package com.malajmi.elm_wallet.user.models;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
public record SignupRequest(
        @NotBlank(message = "username cannot be blank")
        String username,
        @NotBlank(message = "password cannot be blank")
        @Size(min = 6, max = 14, message = "password must between 6 and 14 characters long")
        String password,
        @Email(message = "Invalid email format")
        @NotBlank(message = "email must not be blank")
        String email) {}
