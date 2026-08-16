package com.barbook.booking.shops.controller;

import com.barbook.booking.shops.model.request.CreateShopRequest;
import com.barbook.booking.shops.model.response.ShopResponse;
import com.barbook.booking.shops.service.ShopService;
import com.barbook.booking.shops.utils.ShopEndPointUtils;
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
public class ShopController {

    private final ShopService shopService;

    @PostMapping(ShopEndPointUtils.CREATE_SHOP)
    public ShopResponse createShop(
            @Valid @RequestBody CreateShopRequest request,
            Authentication authentication
    ) {
        return shopService.createShop(authentication.getName(), request);
    }

    @GetMapping(ShopEndPointUtils.GET_SHOP)
    public ShopResponse getMyShop(Authentication authentication) {
        return shopService.getMyShop(authentication.getName());
    }


    // CUSTOMER

    @GetMapping(ShopEndPointUtils.PUBLIC_LIST_SHOPS)
    public List<ShopResponse> listShops() {
        return shopService.listActiveShops();
    }
}
