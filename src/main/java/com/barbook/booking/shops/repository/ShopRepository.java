package com.barbook.booking.shops.repository;

import com.barbook.booking.shops.entity.Shops;
import com.barbook.booking.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shops, Long > {
    Optional<Shops> findByOwner(Users owner);
    boolean existsByOwner(Users owner);
}
