package com.barbook.booking.shops.model.response;

import com.barbook.booking.shops.enums.ShopStatus;

public record ShopResponse(
        Long id,
        String name,
        String address,
        String phone,
        String description,
        ShopStatus status,
        Long ownerId
) {}
