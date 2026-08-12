package com.barbook.booking.auth.dto;

import com.barbook.booking.users.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank String fullName,
        @NotBlank @Email String email,
        @NotBlank String phone,
        @NotBlank @Size(min = 8, message = "Password must be at least 8 characters")
        String password,
        String confirmPassword,
        Role role   // optional: CUSTOMER or SHOP_OWNER; null → CUSTOMER
) {}