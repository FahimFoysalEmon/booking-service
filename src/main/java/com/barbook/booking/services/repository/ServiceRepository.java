package com.barbook.booking.services.repository;

import com.barbook.booking.services.entity.Services;
import com.barbook.booking.services.enums.ServiceStatus;
import com.barbook.booking.shops.entity.Shops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Long> {

    List<Services> findByShop(Shops shop);

    boolean existsByShopAndNameIgnoreCase(Shops shop, String name);

    boolean existsByShopAndNameIgnoreCaseAndIdNot(Shops shop, String name, Long id);

    List<Services> findByShopAndStatus(Shops shop, ServiceStatus status);
}

