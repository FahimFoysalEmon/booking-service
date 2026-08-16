package com.barbook.booking.availability.service;

import com.barbook.booking.availability.entity.Availability;
import com.barbook.booking.availability.enums.AvailabilityStatus;
import com.barbook.booking.availability.model.request.CreateAvailabilityRequest;
import com.barbook.booking.availability.model.response.AvailabilityResponse;
import com.barbook.booking.availability.repository.AvailabilityRepository;
import com.barbook.booking.common.exception.InvalidDataException;
import com.barbook.booking.shops.entity.Shops;
import com.barbook.booking.shops.repository.ShopRepository;
import com.barbook.booking.users.entity.Users;
import com.barbook.booking.users.enums.Role;
import com.barbook.booking.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final ShopRepository shopRepository;
    private final UsersRepository usersRepository;

    public AvailabilityResponse create(String ownerEmail, CreateAvailabilityRequest request) {
        Users owner = getShopOwner(ownerEmail);
        Shops shop = getOwnerShop(owner);

        if (!request.endTime().isAfter(request.startTime())) {
            throw new InvalidDataException("endTime must be after startTime");
        }

        if (availabilityRepository.existsOverlapping(
                shop, AvailabilityStatus.OPEN, request.startTime(), request.endTime())) {
            throw new InvalidDataException("Availability overlaps with an existing open slot");
        }

        Availability availability = new Availability();
        availability.setShop(shop);
        availability.setStartTime(request.startTime());
        availability.setEndTime(request.endTime());
        availability.setStatus(AvailabilityStatus.OPEN);

        return toResponse(availabilityRepository.save(availability));
    }

    public List<AvailabilityResponse> getMyAvailability(String ownerEmail) {
        Users owner = getShopOwner(ownerEmail);
        Shops shop = getOwnerShop(owner);

        return availabilityRepository.findByShop(shop).stream()
                .map(this::toResponse)
                .toList();
    }

    public AvailabilityResponse close(String ownerEmail, Long availabilityId) {
        Users owner = getShopOwner(ownerEmail);
        Shops shop = getOwnerShop(owner);

        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new InvalidDataException("Availability not found"));

        if (!availability.getShop().getId().equals(shop.getId())) {
            throw new InvalidDataException("Availability does not belong to your shop");
        }

        availability.setStatus(AvailabilityStatus.CLOSED);
        return toResponse(availabilityRepository.save(availability));
    }

    private Users getShopOwner(String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidDataException("User not found"));
        if (user.getRole() != Role.SHOP_OWNER) {
            throw new InvalidDataException("Only shop owners can manage availability");
        }
        return user;
    }

    private Shops getOwnerShop(Users owner) {
        return shopRepository.findByOwner(owner)
                .orElseThrow(() -> new InvalidDataException("Create a shop first"));
    }

    private AvailabilityResponse toResponse(Availability availability) {
        return new AvailabilityResponse(
                availability.getId(),
                availability.getShop().getId(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getStatus()
        );
    }
}