package cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.controller;

import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.domain.Curso;
import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public Curso crearCurso(@RequestBody Curso curso) {
        return cursoService.create(curso);
    }

    @GetMapping("/allcursos")
    public List<Curso> obtenerTodos() {
        return cursoService.findAll();
    }

    @GetMapping("/{id}")
    public Curso obtenerPorId(@PathVariable Long id) {
        return cursoService.findById(id);
    }

    @PutMapping("/{id}")
    public void actualizarCurso(@PathVariable Long id, @RequestBody Curso cursoRequest) {
        cursoService.partialUpdate(id, cursoRequest);
    }

    @PatchMapping("/{id}")
    public void actualizarParcialCurso(@PathVariable Long id, @RequestBody Curso cursoRequest) {
        cursoService.partialUpdate(id, cursoRequest);
    }

    @DeleteMapping("/{id}")
    public void eliminarCurso(@PathVariable Long id) {
        cursoService.remove(id);
    }
}
