package com.barbook.booking.auth.service;

import com.barbook.booking.auth.dto.AuthResponse;
import com.barbook.booking.auth.dto.LoginRequest;
import com.barbook.booking.security.JwtService;
import com.barbook.booking.users.entity.Users;
import com.barbook.booking.users.enums.UserStatus;
import com.barbook.booking.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {

        log.info("login request: {}", request);

        Users user = usersRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid email or password"
                ));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid email or password"
            );
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Account is disabled"
            );
        }

        log.info("User logged in: {}", user.getEmail());

        String token = jwtService.generateToken(user);
        return new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole()
        );

    }

}
