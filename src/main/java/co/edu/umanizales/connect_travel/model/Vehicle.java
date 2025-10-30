package co.edu.umanizales.connect_travel.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private Long id;
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private int capacity;
    private String vehicleType; // BUS, VAN, CAR, etc.
    private String status; // AVAILABLE, IN_MAINTENANCE, OUT_OF_SERVICE
    private String color;
}
