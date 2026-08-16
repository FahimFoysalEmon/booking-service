package com.barbook.booking.availability.model.response;

import com.barbook.booking.availability.enums.AvailabilityStatus;

import java.time.LocalDateTime;

public record AvailabilityResponse(
        Long id,
        Long shopId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        AvailabilityStatus status
) {}
