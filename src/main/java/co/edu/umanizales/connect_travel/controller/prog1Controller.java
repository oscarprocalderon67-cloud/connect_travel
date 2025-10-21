package co.edu.umanizales.connect_travel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prog1")


public class prog1Controller {
    @GetMapping

    public String getHello()
    {
        return "Hola Campeones";
    }


}
