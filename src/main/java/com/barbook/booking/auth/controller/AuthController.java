package com.barbook.booking.auth.controller;

import com.barbook.booking.auth.dto.AuthResponse;
import com.barbook.booking.auth.dto.LoginRequest;
import com.barbook.booking.auth.service.AuthService;
import com.barbook.booking.auth.utils.AuthEndPointUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(AuthEndPointUtils.LOGIN_FULL)
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

}
