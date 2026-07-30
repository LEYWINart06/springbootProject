package com.saduguduvandi.controller;

import com.saduguduvandi.entity.Booking;
import com.saduguduvandi.entity.Vehicle;
import com.saduguduvandi.service.AuthService;
import com.saduguduvandi.service.BookingService;
import com.saduguduvandi.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class PageController {

    private final VehicleService vehicleService;
    private final AuthService authService;
    private final BookingService bookingService;

    public PageController(VehicleService vehicleService, AuthService authService, BookingService bookingService) {
        this.vehicleService = vehicleService;
        this.authService = authService;
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new com.saduguduvandi.dto.RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerRequest") com.saduguduvandi.dto.RegisterRequest form,
                            Model model) {
        try {
            authService.register(form.getName(), form.getEmail(), form.getPassword());
            return "redirect:/login?registered";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }

    @GetMapping("/vehicles")
    public String vehicles(@RequestParam(required = false) String category,
                            @RequestParam(required = false) String search,
                            Model model) {
        model.addAttribute("vehicles", vehicleService.search(category, search));
        model.addAttribute("selectedCategory", category == null ? "ALL" : category.toUpperCase());
        model.addAttribute("search", search == null ? "" : search);
        return "vehicles";
    }

    @GetMapping("/vehicles/{id}")
    public String vehicleDetails(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleService.getById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicle-details";
    }

    @GetMapping("/booking/{vehicleId}")
    public String bookingPage(@PathVariable Long vehicleId, Model model) {
        Vehicle vehicle = vehicleService.getById(vehicleId);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("today", LocalDate.now().toString());
        return "booking";
    }

    @PostMapping("/booking")
    public String createBooking(@RequestParam Long vehicleId,
                                 @RequestParam String pickupDate,
                                 @RequestParam String returnDate,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        try {
            bookingService.createBooking(authentication.getName(), vehicleId,
                    LocalDate.parse(pickupDate), LocalDate.parse(returnDate));
            return "redirect:/my-bookings";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/booking/" + vehicleId;
        }
    }

    @GetMapping("/my-bookings")
    public String myBookings(Authentication authentication, Model model) {
        model.addAttribute("bookings", bookingService.getMyBookings(authentication.getName()));
        return "my-bookings";
    }

    @PostMapping("/my-bookings/{id}/cancel")
    public String cancelBooking(@PathVariable Long id, Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        try {
            bookingService.cancelBooking(authentication.getName(), id);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/my-bookings";
    }
}
