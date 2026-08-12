package com.barbook.booking.shops.model.request;

import jakarta.validation.constraints.NotBlank;

public record CreateShopRequest(
        @NotBlank String name,
        String address,
        String phone,
        String description
) {}
