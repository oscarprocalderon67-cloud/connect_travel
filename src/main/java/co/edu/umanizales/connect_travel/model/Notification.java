package co.edu.umanizales.connect_travel.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Long id;
    private User user;
    private String title;
    private String message;
    private LocalDateTime sentAt;
    private boolean isRead;
    private String notificationType; // BOOKING_CONFIRMATION, PAYMENT_RECEIVED, TRIP_REMINDER, etc.
    private String status; // SENT, DELIVERED, READ
}
