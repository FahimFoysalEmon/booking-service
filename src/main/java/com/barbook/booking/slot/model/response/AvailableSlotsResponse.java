package com.barbook.booking.slot.model.response;

import java.time.LocalDateTime;
import java.util.List;

public record AvailableSlotsResponse(
        Long shopId,
        Long serviceId,
        Integer durationMinutes,
        List<LocalDateTime> slots
) {}
