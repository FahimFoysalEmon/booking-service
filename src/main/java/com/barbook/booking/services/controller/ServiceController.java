package com.barbook.booking.services.controller;

import com.barbook.booking.services.model.request.CreateServiceRequest;
import com.barbook.booking.services.model.request.UpdateServiceRequest;
import com.barbook.booking.services.model.response.ServiceResponse;
import com.barbook.booking.services.service.ServiceLayerService;
import com.barbook.booking.services.utils.ServiceEndPointUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceLayerService serviceLayerService;

    @PostMapping(ServiceEndPointUtils.CREATE_SERVICE)
    public ServiceResponse createService(
            @Valid @RequestBody CreateServiceRequest request,
            Authentication authentication
    ) {
        return serviceLayerService.createService(authentication.getName(), request);
    }

    @GetMapping(ServiceEndPointUtils.GET_MY_SERVICES)
    public List<ServiceResponse> getMyServices(Authentication authentication) {
        return serviceLayerService.getMyServices(authentication.getName());
    }

    @PutMapping(ServiceEndPointUtils.UPDATE_SERVICE)
    public ServiceResponse updateService(
            @PathVariable Long serviceId,
            @Valid @RequestBody UpdateServiceRequest request,
            Authentication authentication
    ) {
        return serviceLayerService.updateService(authentication.getName(), serviceId, request);
    }

    @PatchMapping(ServiceEndPointUtils.DISABLE_SERVICE)
    public ServiceResponse disableService(
            @PathVariable Long serviceId,
            Authentication authentication
    ) {
        return serviceLayerService.disableService(authentication.getName(), serviceId);
    }

}