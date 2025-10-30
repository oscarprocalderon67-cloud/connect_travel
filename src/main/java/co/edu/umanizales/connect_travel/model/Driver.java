package co.edu.umanizales.connect_travel.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    private Long id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String documentType; // CC, CE, PASS, etc.
    private LocalDate dateOfBirth;
    private String phone;
    private String email;
    private String licenseNumber;
    private String licenseType;
    private LocalDate licenseExpiry;
    private String status; // ACTIVE, INACTIVE, ON_LEAVE
}
