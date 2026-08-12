package com.barbook.booking.auth.service;

import com.barbook.booking.auth.dto.AuthResponse;
import com.barbook.booking.auth.dto.LoginRequest;
import com.barbook.booking.auth.dto.RegisterRequest;
import com.barbook.booking.common.exception.InvalidDataException;
import com.barbook.booking.security.JwtService;
import com.barbook.booking.users.entity.Users;
import com.barbook.booking.users.enums.Role;
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

        Users user = usersRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidDataException(
                        "Email is not valid"
                ));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidDataException("Password does not match"
            );
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new InvalidDataException("Account is disabled"
            );
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole()
        );
    }



    public AuthResponse register(RegisterRequest request) {

        Role role = request.role() == null ? Role.CUSTOMER : request.role();

        if (role == Role.ADMIN) {
            throw new InvalidDataException("Cannot register as ADMIN");
        }

        if (role != Role.CUSTOMER && role != Role.SHOP_OWNER) {
            throw new InvalidDataException("Invalid role");
        }

        if (usersRepository.existsByEmail(request.email())) {
            throw new InvalidDataException("Email already exists");
        }

        if (usersRepository.existsByPhone(request.phone())) {
            throw new InvalidDataException("Phone already exists");
        }

        if (!request.password().equals(request.confirmPassword())) {
            throw new InvalidDataException("Password did not match");
        }

        Users user = new Users();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);
        user.setStatus(UserStatus.ACTIVE);

        Users saved = usersRepository.save(user);
        String token = jwtService.generateToken(saved);

        return new AuthResponse(
                token,
                saved.getId(),
                saved.getEmail(),
                saved.getFullName(),
                saved.getRole()
        );
    }

}
