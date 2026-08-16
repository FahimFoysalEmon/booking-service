package com.barbook.booking.availability.repository;

import com.barbook.booking.availability.entity.Availability;
import com.barbook.booking.availability.enums.AvailabilityStatus;
import com.barbook.booking.shops.entity.Shops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findByShop(Shops shop);

    List<Availability> findByShopAndStatus(Shops shop, AvailabilityStatus status);

    @Query("""
    select case when count(a) > 0 then true else false end
    from Availability a
    where a.shop = :shop
      and a.status = :status
      and a.startTime < :endTime
      and a.endTime > :startTime
    """)
    boolean existsOverlapping(
            @Param("shop") Shops shop,
            @Param("status") AvailabilityStatus status,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
