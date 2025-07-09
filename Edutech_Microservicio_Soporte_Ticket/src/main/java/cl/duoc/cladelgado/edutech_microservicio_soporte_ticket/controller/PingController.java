package cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public String ping() {
        return "Microservicio Usuario activo ✅";
    }
}