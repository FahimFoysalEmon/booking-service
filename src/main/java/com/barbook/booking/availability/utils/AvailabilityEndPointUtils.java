package com.barbook.booking.availability.utils;

public class AvailabilityEndPointUtils {
    public static final String BASE = "/api/v1/private/availability";
    public static final String CREATE = BASE + "/create";
    public static final String MY_LIST = BASE + "/me";
    public static final String CLOSE = BASE + "/close/{availabilityId}";
}
