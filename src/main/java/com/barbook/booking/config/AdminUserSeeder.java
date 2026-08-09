package com.barbook.booking.config;

import com.barbook.booking.users.entity.Users;
import com.barbook.booking.users.enums.Role;
import com.barbook.booking.users.enums.UserStatus;
import com.barbook.booking.users.repository.UsersRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserSeeder implements ApplicationRunner {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;


    public AdminUserSeeder(
            UsersRepository usersRepository,
            PasswordEncoder passwordEncoder,
            AdminProperties adminProperties
    ) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminProperties = adminProperties;
    }



    @Override
    public void run(ApplicationArguments args) {
        if (usersRepository.existsByRole(Role.ADMIN)) {
            return;
        }
        Users admin = new Users();
        admin.setFullName(adminProperties.fullName());
        admin.setEmail(adminProperties.email());
        admin.setPassword(passwordEncoder.encode(adminProperties.password()));
        admin.setRole(Role.ADMIN);
        admin.setStatus(UserStatus.ACTIVE);
        usersRepository.save(admin);
        System.out.println("Default admin created: " + admin.getEmail());
    }
}
