package com.saduguduvandi.config;

import com.saduguduvandi.entity.Vehicle;
import com.saduguduvandi.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;

    public DataSeeder(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(String... args) {

        // Delete existing vehicle data
        // This allows the updated vehicle names to appear
        vehicleRepository.deleteAll();

        // =========================
        // BIKES
        // =========================

        vehicleRepository.save(new Vehicle(
                "Royal Enfield Classic 350",
                "BIKE",
                500,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTG6xmxAXJxAwLExi_5S55midnHSUlA8W1JFMkxi_QdmfjUqwCSA7UcyN44&s=10"
        ));

        vehicleRepository.save(new Vehicle(
                "Yamaha R15 V4",
                "BIKE",
                400,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnaU1XMJJhq7GX6IyOH6pX0Ldnr-iNPdfH9ksGrEBoaA&s=10"
        ));

        vehicleRepository.save(new Vehicle(
                "TVS Apache RTR 160",
                "BIKE",
                250,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTdEqOfsnisFf44zex6WV0iHzKfBMv3ovGXCg1cw6O8wg&s=10"
        ));


        // =========================
        // CARS
        // =========================

        vehicleRepository.save(new Vehicle(
                "Toyota Innova Crysta",
                "CAR",
                1800,
                "data:image/jpeg;base64,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"
        ));

        vehicleRepository.save(new Vehicle(
                "Maruti Suzuki Swift",
                "CAR",
                1200,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQoI-1HPR5lqu2h49RErw34iKY6fLzLpeLrnkwOo-58iA&s=10"
        ));

        vehicleRepository.save(new Vehicle(
                "Toyota Fortuner",
                "CAR",
                2500,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRRREb08oj-j-1Z3qHuxkMc1CBsDxyTdfFQp1UPTlNbPQ&s=10"
        ));


        // =========================
        // VANS
        // =========================

        vehicleRepository.save(new Vehicle(
                "Force Traveller",
                "VAN",
                3000,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8i7WHKefJNYCG-V_8FKxOMCvvhCmUkvIcbq0S7BdSlQ&s=10"
        ));

        vehicleRepository.save(new Vehicle(
                "Maruti Suzuki Eeco",
                "VAN",
                2200,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQUduIu_v6tNnWKxOYGKO9lZItYPR2q3AiNw4dAEvpXvA&s=10"
        ));

        vehicleRepository.save(new Vehicle(
                "Mahindra Supro",
                "VAN",
                2600,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTRTFkI1mXWUU8gOioHJfGR6Dhwo36FoAADyT9yxIjkbA&s=10"
        ));
    }
}