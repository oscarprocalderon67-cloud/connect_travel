package co.edu.umanizales.connect_travel.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Long id;
    private User user;
    private Trip trip;
    private int rating; // 1-5
    private String comment;
    private LocalDateTime reviewDate;
    private String status; // PENDING, APPROVED, REJECTED
}
