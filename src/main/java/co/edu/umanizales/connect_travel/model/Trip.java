package co.edu.umanizales.connect_travel.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    private Long id;
    private Destination origin;
    private Destination destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int capacity;
    private double price;
    private List<Booking> bookings;
    private String status; // SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
}
