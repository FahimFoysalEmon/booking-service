package com.barbook.booking.bookings.model.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateBookingRequest(
        @NotNull Long shopId,
        @NotNull Long serviceId,
        @NotNull LocalDateTime startTime
) {}