package com.barbook.booking.services.service;

import com.barbook.booking.common.exception.InvalidDataException;
import com.barbook.booking.services.entity.Services;
import com.barbook.booking.services.enums.ServiceStatus;
import com.barbook.booking.services.model.request.CreateServiceRequest;
import com.barbook.booking.services.model.response.ServiceResponse;
import com.barbook.booking.services.repository.ServiceRepository;
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
public class ServiceLayerService {

    private final ServiceRepository serviceRepository;
    private final ShopRepository shopRepository;
    private final UsersRepository usersRepository;

    public ServiceResponse createService(String ownerEmail, CreateServiceRequest request) {

        Users owner = getShopOwner(ownerEmail);
        Shops shop = getOwnerShop(owner);

        if (serviceRepository.existsByShopAndNameIgnoreCase(shop, request.name())) {
            throw new InvalidDataException("Service with this name already exists");
        }

        Services service = new Services();
        service.setName(request.name());
        service.setPrice(request.price());
        service.setDurationMinutes(request.durationMinutes());
        service.setDescription(request.description());
        service.setShop(shop);
        service.setStatus(ServiceStatus.ACTIVE);

        return toResponse(serviceRepository.save(service));
    }
    public List<ServiceResponse> getMyServices(String ownerEmail) {
        Users owner = getShopOwner(ownerEmail);
        Shops shop = getOwnerShop(owner);
        return serviceRepository.findByShop(shop).stream()
                .map(this::toResponse)
                .toList();
    }

    private Users getShopOwner(String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidDataException("User not found"));
        if (user.getRole() != Role.SHOP_OWNER) {
            throw new InvalidDataException("Only shop owners can manage services");
        }
        return user;
    }

    private Shops getOwnerShop(Users owner) {
        return shopRepository.findByOwner(owner)
                .orElseThrow(() -> new InvalidDataException("Create a shop first"));
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
