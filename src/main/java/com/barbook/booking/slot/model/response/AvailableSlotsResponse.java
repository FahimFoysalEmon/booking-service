package com.barbook.booking.slot.model.response;
import java.util.List;

public record AvailableSlotsResponse(
        Long shopId,
        Long serviceId,
        Integer durationMinutes,
        List<SlotItemResponse> slots
) {}
