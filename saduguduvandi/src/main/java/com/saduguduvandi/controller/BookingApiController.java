package com.saduguduvandi.controller;

import com.saduguduvandi.dto.BookingRequest;
import com.saduguduvandi.entity.Booking;
import com.saduguduvandi.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingApiController {

    private final BookingService bookingService;

    public BookingApiController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking create(@Valid @RequestBody BookingRequest request, Authentication authentication) {
        return bookingService.createBooking(authentication.getName(), request.getVehicleId(),
                request.getPickupDate(), request.getReturnDate());
    }

    @GetMapping("/my")
    public List<Booking> myBookings(Authentication authentication) {
        return bookingService.getMyBookings(authentication.getName());
    }

    @PutMapping("/{id}/cancel")
    public Map<String, String> cancel(@PathVariable Long id, Authentication authentication) {
        bookingService.cancelBooking(authentication.getName(), id);
        return Map.of("message", "Booking cancelled successfully");
    }
}
