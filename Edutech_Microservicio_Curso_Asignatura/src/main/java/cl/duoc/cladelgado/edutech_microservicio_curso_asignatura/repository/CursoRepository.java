package cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.repository;

import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.domain.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}