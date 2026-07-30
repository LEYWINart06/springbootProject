package com.saduguduvandi.service;

import com.saduguduvandi.entity.Vehicle;
import com.saduguduvandi.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> search(String category, String name) {
        boolean hasCategory = StringUtils.hasText(category) && !category.equalsIgnoreCase("ALL");
        boolean hasName = StringUtils.hasText(name);

        if (hasCategory && hasName) {
            return vehicleRepository.findByCategoryIgnoreCaseAndNameContainingIgnoreCase(category, name);
        } else if (hasCategory) {
            return vehicleRepository.findByCategoryIgnoreCase(category);
        } else if (hasName) {
            return vehicleRepository.findByNameContainingIgnoreCase(name);
        }
        return vehicleRepository.findAll();
    }

    public Vehicle getById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
    }

    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }
}
