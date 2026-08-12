package com.barbook.booking.auth.controller;

import com.barbook.booking.auth.dto.AuthResponse;
import com.barbook.booking.auth.dto.LoginRequest;
import com.barbook.booking.auth.dto.RegisterRequest;
import com.barbook.booking.auth.service.AuthService;
import com.barbook.booking.auth.utils.AuthEndPointUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(AuthEndPointUtils.LOGIN_FULL)
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }


    @PostMapping(AuthEndPointUtils.REGISTER_FULL)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }


    @GetMapping(AuthEndPointUtils.ME_FULL)
    public String me(Authentication authentication) {
        return "Logged in as: " + authentication.getName();
    }

}
