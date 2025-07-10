package cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public String ping() {
        return "Microservicio Usuario activo ✅";
    }
}
