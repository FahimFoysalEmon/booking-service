package com.barbook.booking.slot.service;

import com.barbook.booking.availability.entity.Availability;
import com.barbook.booking.availability.enums.AvailabilityStatus;
import com.barbook.booking.availability.repository.AvailabilityRepository;
import com.barbook.booking.common.exception.InvalidDataException;
import com.barbook.booking.services.entity.Services;
import com.barbook.booking.services.enums.ServiceStatus;
import com.barbook.booking.services.model.response.ServiceResponse;
import com.barbook.booking.services.repository.ServiceRepository;
import com.barbook.booking.shops.entity.Shops;
import com.barbook.booking.shops.enums.ShopStatus;
import com.barbook.booking.shops.repository.ShopRepository;
import com.barbook.booking.slot.model.response.AvailableSlotsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlotService {


    private final ShopRepository shopRepository;
    private final AvailabilityRepository availabilityRepository;
    private final ServiceRepository serviceRepository;


    // CUSTOMER APIS

    public List<ServiceResponse> listActiveServicesByShop(Long shopId) {
        Shops shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new InvalidDataException("Shop not found"));

        if (shop.getStatus() != ShopStatus.ACTIVE) {
            throw new InvalidDataException("Shop is not active");
        }

        return serviceRepository.findByShopAndStatus(shop, ServiceStatus.ACTIVE).stream()
                .map(this::toResponse)
                .toList();
    }


    private ServiceResponse toResponse(Services service) {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getPrice(),
                service.getDurationMinutes(),
                service.getDescription(),
                service.getStatus(),
                service.getShop().getId()
        );
    }


    public AvailableSlotsResponse getAvailableSlots(Long shopId, Long serviceId) {
        Shops shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new InvalidDataException("Shop not found"));

        Services service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new InvalidDataException("Service not found"));

        if (!service.getShop().getId().equals(shopId)) {
            throw new InvalidDataException("Service does not belong to this shop");
        }
        if (service.getStatus() != ServiceStatus.ACTIVE) {
            throw new InvalidDataException("Service is not active");
        }

        int duration = service.getDurationMinutes();
        int stepMinutes = 20; // slot interval

        List<Availability> windows = availabilityRepository
                .findByShopAndStatus(shop, AvailabilityStatus.OPEN);

        List<LocalDateTime> slots = new ArrayList<>();

        for (Availability window : windows) {
            LocalDateTime cursor = window.getStartTime();
            while (!cursor.plusMinutes(duration).isAfter(window.getEndTime())) {
                slots.add(cursor);
                cursor = cursor.plusMinutes(stepMinutes);
            }
        }

        return new AvailableSlotsResponse(shopId, serviceId, duration, slots);
    }


}
