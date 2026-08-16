package com.barbook.booking.services.model.response;

import com.barbook.booking.services.enums.ServiceStatus;

import java.math.BigDecimal;

public record ServiceResponse(
        Long id,
        String name,
        BigDecimal price,
        Integer durationMinutes,
        String description,
        ServiceStatus status,
        Long shopId
) {}
