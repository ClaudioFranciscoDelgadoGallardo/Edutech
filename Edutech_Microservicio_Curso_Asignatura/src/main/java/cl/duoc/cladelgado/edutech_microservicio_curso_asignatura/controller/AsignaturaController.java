package cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.controller;

import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.domain.Asignatura;
import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @PostMapping
    public Asignatura crearAsignatura(@RequestBody Asignatura asignatura) {
        return asignaturaService.create(asignatura);
    }

    @GetMapping("/allasignaturas")
    public List<Asignatura> obtenerTodas() {
        return asignaturaService.findAll();
    }

    @GetMapping("/{id}")
    public Asignatura obtenerPorId(@PathVariable Long id) {
        return asignaturaService.findById(id);
    }

    @PutMapping("/{id}")
    public void actualizarAsignatura(@PathVariable Long id, @RequestBody Asignatura req) {
        asignaturaService.partialUpdate(id, req);
    }

    @PatchMapping("/{id}")
    public void actualizarParcialAsignatura(@PathVariable Long id, @RequestBody Asignatura req) {
        asignaturaService.partialUpdate(id, req);
    }

    @DeleteMapping("/{id}")
    public void eliminarAsignatura(@PathVariable Long id) {
        asignaturaService.remove(id);
    }
}
