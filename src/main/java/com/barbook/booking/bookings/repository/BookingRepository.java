package com.barbook.booking.bookings.repository;

import com.barbook.booking.bookings.entity.Bookings;
import com.barbook.booking.bookings.enums.BookingStatus;
import com.barbook.booking.shops.entity.Shops;
import com.barbook.booking.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Bookings, Long> {

    List<Bookings> findByCustomer(Users customer);

    List<Bookings> findByShop(Shops shop);

    @Query("""
        select case when count(b) > 0 then true else false end
        from Bookings b
        where b.shop = :shop
          and b.status = :status
          and b.startTime < :endTime
          and b.endTime > :startTime
        """)
    boolean existsOverlapping(
            @Param("shop") Shops shop,
            @Param("status") BookingStatus status,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

}
