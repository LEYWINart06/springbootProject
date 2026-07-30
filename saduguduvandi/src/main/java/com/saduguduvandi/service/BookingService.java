package com.saduguduvandi.service;

import com.saduguduvandi.entity.Booking;
import com.saduguduvandi.entity.BookingStatus;
import com.saduguduvandi.entity.User;
import com.saduguduvandi.entity.Vehicle;
import com.saduguduvandi.repository.BookingRepository;
import com.saduguduvandi.repository.UserRepository;
import com.saduguduvandi.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, VehicleRepository vehicleRepository,
                           UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    public Booking createBooking(String userEmail, Long vehicleId, LocalDate pickupDate, LocalDate returnDate) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        if (pickupDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Pickup date cannot be in the past");
        }
        if (!returnDate.isAfter(pickupDate)) {
            throw new IllegalArgumentException("Return date must be after pickup date");
        }

        List<Booking> existingBookings = bookingRepository.findByVehicleIdAndStatus(vehicleId, BookingStatus.CONFIRMED);
        for (Booking existing : existingBookings) {
            boolean overlaps = !pickupDate.isAfter(existing.getReturnDate())
                    && !returnDate.isBefore(existing.getPickupDate());
            if (overlaps) {
                throw new IllegalArgumentException("This vehicle is already booked for the selected dates");
            }
        }

        long days = java.time.temporal.ChronoUnit.DAYS.between(pickupDate, returnDate);
        double totalPrice = days * vehicle.getPricePerDay();

        Booking booking = new Booking(user, vehicle, pickupDate, returnDate, totalPrice, BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public List<Booking> getMyBookings(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return bookingRepository.findByUserIdOrderByIdDesc(user.getId());
    }

    public void cancelBooking(String userEmail, Long bookingId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You are not allowed to cancel this booking");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
