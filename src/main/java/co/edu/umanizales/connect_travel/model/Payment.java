package co.edu.umanizales.connect_travel.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private Long id;
    private Booking booking;
    private double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod; // CREDIT_CARD, DEBIT_CARD, CASH, PAYPAL
    private String status; // PENDING, COMPLETED, FAILED, REFUNDED
    private String transactionId;
}
