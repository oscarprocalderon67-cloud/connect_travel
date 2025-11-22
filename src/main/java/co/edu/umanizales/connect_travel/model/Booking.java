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
    private Long userId;  // Solo guardamos el ID del usuario
    private Long tripId;  // Solo guardamos el ID del viaje
    private LocalDateTime bookingDate;
    private int passengerCount;
    private double totalPrice;
    private String status = "PENDING"; // Valor por defecto

    @Override
    public String toString() {
        return String.format("%d,%d,%d,%s,%d,%.2f,%s",
            id != null ? id : "",
            userId != null ? userId : "",
            tripId != null ? tripId : "",
            bookingDate != null ? bookingDate.toString() : "",
            passengerCount,
            totalPrice,
            status
        );
    }
    
    public static Booking valueOf(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            return null;
        }
        
        String[] parts = csvLine.split(",");
        if (parts.length < 7) {
            return null;
        }
        
        try {
            Booking booking = new Booking();
            booking.setId(parts[0].isEmpty() ? null : Long.parseLong(parts[0]));
            booking.setUserId(parts[1].isEmpty() ? null : Long.parseLong(parts[1]));
            booking.setTripId(parts[2].isEmpty() ? null : Long.parseLong(parts[2]));
            booking.setBookingDate(parts[3].isEmpty() ? LocalDateTime.now() : LocalDateTime.parse(parts[3]));
            booking.setPassengerCount(parts[4].isEmpty() ? 1 : Integer.parseInt(parts[4]));
            booking.setTotalPrice(parts[5].isEmpty() ? 0.0 : Double.parseDouble(parts[5]));
            booking.setStatus(parts[6].isEmpty() ? "PENDING" : parts[6]);
            
            return booking;
        } catch (Exception e) {
            System.err.println("Error parsing Booking from CSV: " + e.getMessage());
            return null;
        }
    }
}
