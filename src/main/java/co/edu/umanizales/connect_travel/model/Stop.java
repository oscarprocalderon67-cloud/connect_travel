package co.edu.umanizales.connect_travel.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stop {
    private Long id;
    private String name;
    private String description;
    private String address;
    private double latitude;
    private double longitude;
    private int stopOrder; // Order in the route
    private String status; // ACTIVE, INACTIVE
    private boolean isTerminal; // If it's a start or end point
}
