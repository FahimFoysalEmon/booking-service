package com.barbook.booking.bookings.model.response;

import com.barbook.booking.bookings.enums.BookingStatus;

import java.time.LocalDateTime;

public record BookingResponse(
        Long id,
        Long shopId,
        Long serviceId,
        String serviceName,
        Long customerId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        BookingStatus status
) {}