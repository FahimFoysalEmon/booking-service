package com.barbook.booking.services.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateServiceRequest(
        @NotBlank String name,
        @NotNull @Min(0) BigDecimal price,
        @NotNull @Min(1) Integer durationMinutes,
        String description
) {}
