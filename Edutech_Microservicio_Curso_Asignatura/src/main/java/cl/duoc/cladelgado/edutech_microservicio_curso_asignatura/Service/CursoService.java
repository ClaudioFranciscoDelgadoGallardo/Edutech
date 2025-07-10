package cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service;
import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.repository.AsignaturaRepository;
import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.repository.CursoRepository;
import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.domain.Curso;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CursoService {

    private final CursoRepository cursoRepository;
    private final AsignaturaRepository asignaturaRepository;

    @Autowired
    public CursoService(CursoRepository cursoRepository,
                        AsignaturaRepository asignaturaRepository) {
        this.cursoRepository      = cursoRepository;
        this.asignaturaRepository = asignaturaRepository;
    }

    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    public Curso findById(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
    }

    public Curso create(Curso curso) {
        if (cursoRepository.existsById(curso.getCurso())) {
            throw new RuntimeException("Ya existe un curso con ID: " + curso.getCurso());
        }
        return cursoRepository.save(curso);
    }

    public void partialUpdate(Long id, Curso cursoRequest) {
        Curso existing = findById(id);
        if (cursoRequest.getNombreCurso() != null) {
            existing.setNombreCurso(cursoRequest.getNombreCurso());
        }
        if (cursoRequest.getRutUsuario() != null) {
            existing.setRutUsuario(cursoRequest.getRutUsuario());
        }
        cursoRepository.save(existing);
    }

    public void remove(Long id) {
        if (asignaturaRepository.existsByCurso(id)) {
            throw new RuntimeException(
                    "No se puede eliminar el curso con ID: " + id +
                            " porque tiene asignaturas asociadas"
            );
        }
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso no encontrado con ID: " + id);
        }
        cursoRepository.deleteById(id);
    }
}
