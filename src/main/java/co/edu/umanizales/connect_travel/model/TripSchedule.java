package co.edu.umanizales.connect_travel.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripSchedule {
    private Long id;
    private Trip trip;
    private String dayOfWeek; // MONDAY, TUESDAY, etc.
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Driver driver;
    private Vehicle vehicle;
    private String status; // ACTIVE, CANCELLED, COMPLETED
    private int availableSeats;
}
