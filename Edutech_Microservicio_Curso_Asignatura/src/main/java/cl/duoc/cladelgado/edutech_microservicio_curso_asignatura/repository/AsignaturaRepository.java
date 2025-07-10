package cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.repository;

import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.domain.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    boolean existsByCurso(Long cursoId);
}
