package com.barbook.booking.auth.dto;

import com.barbook.booking.users.enums.Role;

public record AuthResponse(
        String accessToken,
        Long userId,
        String email,
        String fullName,
        Role role
) {}
