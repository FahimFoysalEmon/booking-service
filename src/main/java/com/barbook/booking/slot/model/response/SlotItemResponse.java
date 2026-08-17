package com.barbook.booking.slot.model.response;
import java.time.LocalDateTime;

public record SlotItemResponse(
        LocalDateTime startTime,
        LocalDateTime endTime
) {}