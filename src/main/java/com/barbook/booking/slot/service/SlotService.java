package com.barbook.booking.slot.service;

import com.barbook.booking.availability.entity.Availability;
import com.barbook.booking.availability.enums.AvailabilityStatus;
import com.barbook.booking.availability.repository.AvailabilityRepository;
import com.barbook.booking.bookings.enums.BookingStatus;
import com.barbook.booking.bookings.repository.BookingRepository;
import com.barbook.booking.common.exception.InvalidDataException;
import com.barbook.booking.services.entity.Services;
import com.barbook.booking.services.enums.ServiceStatus;
import com.barbook.booking.services.model.response.ServiceResponse;
import com.barbook.booking.services.repository.ServiceRepository;
import com.barbook.booking.shops.entity.Shops;
import com.barbook.booking.shops.enums.ShopStatus;
import com.barbook.booking.shops.repository.ShopRepository;
import com.barbook.booking.slot.model.response.AvailableSlotsResponse;
import com.barbook.booking.slot.model.response.SlotItemResponse;
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
    private final BookingRepository bookingRepository;

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

    public AvailableSlotsResponse getAvailableSlots(Long shopId, Long serviceId) {
        Shops shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new InvalidDataException("Shop not found"));

        if (shop.getStatus() != ShopStatus.ACTIVE) {
            throw new InvalidDataException("Shop is not active");
        }

        Services service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new InvalidDataException("Service not found"));

        if (!service.getShop().getId().equals(shopId)) {
            throw new InvalidDataException("Service does not belong to this shop");
        }
        if (service.getStatus() != ServiceStatus.ACTIVE) {
            throw new InvalidDataException("Service is not active");
        }

        int duration = service.getDurationMinutes();
        int stepMinutes = 20;

        List<Availability> windows = availabilityRepository
                .findByShopAndStatus(shop, AvailabilityStatus.OPEN);

        List<SlotItemResponse> slots = new ArrayList<>();

        for (Availability window : windows) {
            LocalDateTime cursor = window.getStartTime();
            while (!cursor.plusMinutes(duration).isAfter(window.getEndTime())) {
                LocalDateTime slotEnd = cursor.plusMinutes(duration);

                boolean isBooked = bookingRepository.existsOverlapping(
                        shop,
                        BookingStatus.BOOKED,
                        cursor,
                        slotEnd
                );

                if (!isBooked) {
                    slots.add(new SlotItemResponse(cursor, slotEnd));
                }

                cursor = cursor.plusMinutes(stepMinutes);
            }
        }

        return new AvailableSlotsResponse(shopId, serviceId, duration, slots);
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
}