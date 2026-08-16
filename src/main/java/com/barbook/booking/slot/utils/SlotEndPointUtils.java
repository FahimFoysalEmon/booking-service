package com.barbook.booking.slot.utils;

public class SlotEndPointUtils {


    // CUSTOMER FETCHING
    public static final String PUBLIC_SERVICES_BY_SHOP =
            "/api/v1/public/shops/{shopId}/services";

    public static final String PUBLIC_SLOTS =
            "/api/v1/public/shops/{shopId}/services/{serviceId}/slots";

}
