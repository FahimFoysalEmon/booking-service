package com.barbook.booking.bookings.controller;

import com.barbook.booking.bookings.model.request.CreateBookingRequest;
import com.barbook.booking.bookings.model.response.BookingResponse;
import com.barbook.booking.bookings.service.BookingService;
import com.barbook.booking.bookings.utils.BookingsEndPointUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    //CUSTOMER CREATE BOOKING
    @PostMapping(BookingsEndPointUtils.CREATE)
    public BookingResponse customerCreateBooking(
            @Valid @RequestBody CreateBookingRequest request,
            Authentication authentication
    ) {
        return bookingService.create(authentication.getName(), request);
    }


    //CUSTOMER BOOKINGS
    @GetMapping(BookingsEndPointUtils.MY_BOOKINGS)
    public List<BookingResponse> customerBookings(Authentication authentication) {
        return bookingService.myBookings(authentication.getName());
    }

    //SHOP OWNER SEE BOOKINGS
    @GetMapping(BookingsEndPointUtils.SHOP_BOOKINGS)
    public List<BookingResponse> shopOwnerBookings(Authentication authentication) {
        return bookingService.shopBookings(authentication.getName());
    }
}