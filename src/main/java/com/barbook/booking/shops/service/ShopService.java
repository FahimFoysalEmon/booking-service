package com.barbook.booking.shops.service;

import com.barbook.booking.common.exception.InvalidDataException;
import com.barbook.booking.shops.entity.Shops;
import com.barbook.booking.shops.enums.ShopStatus;
import com.barbook.booking.shops.model.request.CreateShopRequest;
import com.barbook.booking.shops.model.response.ShopResponse;
import com.barbook.booking.shops.repository.ShopRepository;
import com.barbook.booking.users.entity.Users;
import com.barbook.booking.users.enums.Role;
import com.barbook.booking.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final UsersRepository usersRepository;

    public ShopResponse createShop(String ownerEmail, CreateShopRequest request) {
        Users owner = usersRepository.findByEmailOrPhone(ownerEmail, request.phone())
                .orElseThrow(() -> new InvalidDataException("User not found"));

        if (owner.getRole() != Role.SHOP_OWNER) {
            throw new InvalidDataException("Only shop owners can create a shop");
        }

        if (shopRepository.existsByOwner(owner)) {
            throw new InvalidDataException("Shop already exists for this owner");
        }

        Shops shop = new Shops();
        shop.setName(request.name());
        shop.setAddress(request.address());
        shop.setPhone(request.phone());
        shop.setDescription(request.description());
        shop.setOwner(owner);
        shop.setStatus(ShopStatus.ACTIVE);

        Shops saved = shopRepository.save(shop);
        return toResponse(saved);
    }

    public ShopResponse getMyShop(String ownerEmail) {
        Users owner = usersRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new InvalidDataException("User not found"));

        Shops shop = shopRepository.findByOwner(owner)
                .orElseThrow(() -> new InvalidDataException("Shop not found"));

        return toResponse(shop);
    }

    private ShopResponse toResponse(Shops shop) {
        return new ShopResponse(
                shop.getId(),
                shop.getName(),
                shop.getAddress(),
                shop.getPhone(),
                shop.getDescription(),
                shop.getStatus(),
                shop.getOwner().getId()
        );
    }



    // CUSTOMER

    public List<ShopResponse> listActiveShops() {
        return shopRepository.findByStatus(ShopStatus.ACTIVE).stream()
                .map(this::toResponse)
                .toList();
    }
}