package com.barbook.booking.users.repository;

import com.barbook.booking.users.entity.Users;
import com.barbook.booking.users.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmailOrPhone(String email, String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByRole(Role role);

    Optional<Users> findByEmail(String email);
}