package com.barbook.booking.shops.utils;

public class ShopEndPointUtils {

    public static final String SHOP_BASE = "/api/v1/private/shop";

    //FOR SHOP_OWNER
    public static final String CREATE_SHOP = SHOP_BASE + "/create";
    public static final String GET_SHOP = SHOP_BASE + "/me";

    //FOR CUSTOMER
    public static final String PUBLIC_SHOP_BASE = "/api/v1/public/shops";
    public static final String PUBLIC_LIST_SHOPS = PUBLIC_SHOP_BASE;
}
