package cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.repository;

import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsBySoporte(String soporte);
}
