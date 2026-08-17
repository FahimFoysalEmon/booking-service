package com.barbook.booking.bookings.service;

import com.barbook.booking.availability.enums.AvailabilityStatus;
import com.barbook.booking.availability.repository.AvailabilityRepository;
import com.barbook.booking.bookings.entity.Bookings;
import com.barbook.booking.bookings.enums.BookingStatus;
import com.barbook.booking.bookings.model.request.CreateBookingRequest;
import com.barbook.booking.bookings.model.response.BookingResponse;
import com.barbook.booking.bookings.repository.BookingRepository;
import com.barbook.booking.common.exception.InvalidDataException;
import com.barbook.booking.services.entity.Services;
import com.barbook.booking.services.enums.ServiceStatus;
import com.barbook.booking.services.repository.ServiceRepository;
import com.barbook.booking.shops.entity.Shops;
import com.barbook.booking.shops.repository.ShopRepository;
import com.barbook.booking.users.entity.Users;
import com.barbook.booking.users.enums.Role;
import com.barbook.booking.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShopRepository shopRepository;
    private final ServiceRepository serviceRepository;
    private final UsersRepository usersRepository;
    private final AvailabilityRepository availabilityRepository;

    public BookingResponse create(String customerEmail, CreateBookingRequest request) {
        Users customer = usersRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new InvalidDataException("User not found"));

        if (customer.getRole() != Role.CUSTOMER) {
            throw new InvalidDataException("Only customers can create bookings");
        }

        Shops shop = shopRepository.findById(request.shopId())
                .orElseThrow(() -> new InvalidDataException("Shop not found"));

        Services service = serviceRepository.findById(request.serviceId())
                .orElseThrow(() -> new InvalidDataException("Service not found"));

        if (!service.getShop().getId().equals(shop.getId())) {
            throw new InvalidDataException("Service does not belong to this shop");
        }
        if (service.getStatus() != ServiceStatus.ACTIVE) {
            throw new InvalidDataException("Service is not active");
        }

        LocalDateTime start = request.startTime();
        LocalDateTime end = start.plusMinutes(service.getDurationMinutes());

        // must fit inside an OPEN availability window
        boolean fitsAvailability = availabilityRepository
                .findByShopAndStatus(shop, AvailabilityStatus.OPEN)
                .stream()
                .anyMatch(a -> !start.isBefore(a.getStartTime()) && !end.isAfter(a.getEndTime()));

        if (!fitsAvailability) {
            throw new InvalidDataException("Selected time is outside shop availability");
        }

        if (bookingRepository.existsOverlapping(shop, BookingStatus.BOOKED, start, end)) {
            throw new InvalidDataException("This time slot is already booked");
        }

        Bookings booking = new Bookings();
        booking.setShop(shop);
        booking.setService(service);
        booking.setCustomer(customer);
        booking.setStartTime(start);
        booking.setEndTime(end);
        booking.setStatus(BookingStatus.BOOKED);

        return toResponse(bookingRepository.save(booking));
    }

    public List<BookingResponse> myBookings(String customerEmail) {
        Users customer = usersRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new InvalidDataException("User not found"));

        return bookingRepository.findByCustomer(customer).stream()
                .map(this::toResponse)
                .toList();
    }


    public List<BookingResponse> shopBookings(String ownerEmail) {
        Users owner = usersRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new InvalidDataException("User not found"));

        if (owner.getRole() != Role.SHOP_OWNER) {
            throw new InvalidDataException("Only shop owners can view shop bookings");
        }

        Shops shop = shopRepository.findByOwner(owner)
                .orElseThrow(() -> new InvalidDataException("Shop not found for this owner"));

        return bookingRepository.findByShop(shop).stream()
                .map(this::toResponse)
                .toList();
    }

    private BookingResponse toResponse(Bookings booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getShop().getId(),
                booking.getService().getId(),
                booking.getService().getName(),
                booking.getCustomer().getId(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getStatus()
        );
    }
}