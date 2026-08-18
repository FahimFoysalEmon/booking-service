package com.barbook.booking.slot.controller;


import com.barbook.booking.services.model.response.ServiceResponse;
import com.barbook.booking.services.utils.ServiceEndPointUtils;
import com.barbook.booking.slot.model.response.AvailableSlotsResponse;
import com.barbook.booking.slot.service.SlotService;
import com.barbook.booking.slot.utils.SlotEndPointUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SlotBookingController {

    private final SlotService slotService;

    //FOR CUSTOMER
    @GetMapping(SlotEndPointUtils.PUBLIC_SERVICES_BY_SHOP)
    public List<ServiceResponse> listServicesByShop(@PathVariable Long shopId) {
        return slotService.listActiveServicesByShop(shopId);
    }

    //FOR CUSTOMER
    @GetMapping(SlotEndPointUtils.PUBLIC_SLOTS)
    public AvailableSlotsResponse getSlots(
            @PathVariable Long shopId,
            @PathVariable Long serviceId
    ) {
        return slotService.getAvailableSlots(shopId, serviceId);
    }

}
