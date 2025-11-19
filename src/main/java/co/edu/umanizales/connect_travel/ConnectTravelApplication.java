package co.edu.umanizales.connect_travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import co.edu.umanizales.connect_travel.config.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ConnectTravelApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConnectTravelApplication.class, args);
    }

}
