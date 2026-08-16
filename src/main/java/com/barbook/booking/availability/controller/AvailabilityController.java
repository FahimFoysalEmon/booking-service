package com.barbook.booking.availability.controller;

import com.barbook.booking.availability.model.request.CreateAvailabilityRequest;
import com.barbook.booking.availability.model.response.AvailabilityResponse;
import com.barbook.booking.availability.service.AvailabilityService;
import com.barbook.booking.availability.utils.AvailabilityEndPointUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping(AvailabilityEndPointUtils.CREATE)
    public AvailabilityResponse create(
            @Valid @RequestBody CreateAvailabilityRequest request,
            Authentication authentication
    ) {
        return availabilityService.create(authentication.getName(), request);
    }


    @GetMapping(AvailabilityEndPointUtils.MY_LIST)
    public List<AvailabilityResponse> myList(Authentication authentication) {
        return availabilityService.getMyAvailability(authentication.getName());
    }


    @PatchMapping(AvailabilityEndPointUtils.CLOSE)
    public AvailabilityResponse close(
            @PathVariable Long availabilityId,
            Authentication authentication
    ) {
        return availabilityService.close(authentication.getName(), availabilityId);
    }
}