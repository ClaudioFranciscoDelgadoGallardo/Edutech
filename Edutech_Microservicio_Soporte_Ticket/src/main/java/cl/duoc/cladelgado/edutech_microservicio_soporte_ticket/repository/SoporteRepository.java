package cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.repository;

import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.domain.Soporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, String> {
}
