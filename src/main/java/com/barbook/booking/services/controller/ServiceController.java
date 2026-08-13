package com.barbook.booking.services.controller;

import com.barbook.booking.services.model.request.CreateServiceRequest;
import com.barbook.booking.services.model.response.ServiceResponse;
import com.barbook.booking.services.service.ServiceLayerService;
import com.barbook.booking.services.utils.ServiceEndPointUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}