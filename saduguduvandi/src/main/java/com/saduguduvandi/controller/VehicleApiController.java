package com.saduguduvandi.controller;

import com.saduguduvandi.entity.Vehicle;
import com.saduguduvandi.service.VehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleApiController {

    private final VehicleService vehicleService;

    public VehicleApiController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public List<Vehicle> getAll(@RequestParam(required = false) String category,
                                 @RequestParam(required = false) String search) {
        return vehicleService.search(category, search);
    }

    @GetMapping("/{id}")
    public Vehicle getById(@PathVariable Long id) {
        return vehicleService.getById(id);
    }
}
