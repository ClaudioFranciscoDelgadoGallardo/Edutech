package cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service;
import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.repository.AsignaturaRepository;
import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.repository.CursoRepository;
import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.domain.Asignatura;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AsignaturaService {

    private final AsignaturaRepository asignaturaRepository;
    private final CursoRepository cursoRepository;

    @Autowired
    public AsignaturaService(AsignaturaRepository asignaturaRepository,
                             CursoRepository cursoRepository) {
        this.asignaturaRepository = asignaturaRepository;
        this.cursoRepository      = cursoRepository;
    }

    public List<Asignatura> findAll() {
        return asignaturaRepository.findAll();
    }

    public Asignatura findById(Long id) {
        return asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con ID: " + id));
    }

    public Asignatura create(Asignatura asignatura) {
        if (asignaturaRepository.existsById(asignatura.getAsignatura())) {
            throw new RuntimeException("Ya existe una asignatura con ID: " + asignatura.getAsignatura());
        }
        if (!cursoRepository.existsById(asignatura.getCurso())) {
            throw new RuntimeException("No existe curso con ID: " + asignatura.getCurso());
        }
        return asignaturaRepository.save(asignatura);
    }

    public void partialUpdate(Long id, Asignatura req) {
        Asignatura existing = findById(id);
        if (req.getCurso() != null) {
            if (!cursoRepository.existsById(req.getCurso())) {
                throw new RuntimeException("No existe curso con ID: " + req.getCurso());
            }
            existing.setCurso(req.getCurso());
        }
        asignaturaRepository.save(existing);
    }

    public void remove(Long id) {
        if (!asignaturaRepository.existsById(id)) {
            throw new RuntimeException("Asignatura no encontrada con ID: " + id);
        }
        asignaturaRepository.deleteById(id);
    }
}