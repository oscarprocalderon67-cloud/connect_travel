package co.edu.umanizales.connect_travel.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private Long id;
    private User user;
    private Trip trip;
    private LocalDateTime bookingDate;
    private int passengerCount;
    private double totalPrice;
    private String status; // PENDING, CONFIRMED, CANCELLED, COMPLETED
    private String specialRequests;
}
