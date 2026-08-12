package com.barbook.booking.auth.utils;

public class AuthEndPointUtils {

    public static final String AUTH_BASE = "/api/v1/auth";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String ME = "/me";


    // Full paths — useful in SecurityConfig
    public static final String LOGIN_FULL = AUTH_BASE + LOGIN;
    public static final String REGISTER_FULL = AUTH_BASE + REGISTER;
    public static final String ME_FULL = AUTH_BASE + ME;


}
