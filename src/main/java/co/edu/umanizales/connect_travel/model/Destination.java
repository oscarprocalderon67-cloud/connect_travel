package co.edu.umanizales.connect_travel.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Destination {
    private Long id;
    private String name;
    private String city;
    private String country;
    private String description;
    private boolean active = true;
}
