package com.barbook.booking.services.repository;

import com.barbook.booking.services.entity.Services;
import com.barbook.booking.shops.entity.Shops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Integer> {
    List<Services> findByShop(Shops shop);
}

