package com.barbook.booking.availability.model.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateAvailabilityRequest(
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime
) {}
