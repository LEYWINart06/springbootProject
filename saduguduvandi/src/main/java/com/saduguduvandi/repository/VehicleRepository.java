package com.saduguduvandi.repository;

import com.saduguduvandi.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByCategoryIgnoreCase(String category);

    List<Vehicle> findByNameContainingIgnoreCase(String name);

    List<Vehicle> findByCategoryIgnoreCaseAndNameContainingIgnoreCase(String category, String name);
}
