package com.barbook.booking.services.utils;

public class ServiceEndPointUtils {

    // SHOP OWNER
    public static final String SERVICE_BASE = "/api/v1/private/service";
    public static final String CREATE_SERVICE = SERVICE_BASE + "/create";
    public static final String GET_MY_SERVICES = SERVICE_BASE + "/me";

    public static final String UPDATE_SERVICE = SERVICE_BASE + "/update/{serviceId}";
    public static final String DISABLE_SERVICE = SERVICE_BASE + "/disable/{serviceId}";


}
