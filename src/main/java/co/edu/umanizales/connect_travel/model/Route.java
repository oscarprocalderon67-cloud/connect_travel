package co.edu.umanizales.connect_travel.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    private Long id;
    private String name;
    private String description;
    private List<Stop> stops;
    private double distance; // in kilometers
    private double estimatedDuration; // in minutes
    private String status; // ACTIVE, INACTIVE
    private double basePrice;
}
