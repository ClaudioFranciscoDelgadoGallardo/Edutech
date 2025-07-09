package cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service;

import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.repository.SoporteRepository;
import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.repository.TicketRepository;
import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.domain.Soporte;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SoporteService {

    private final SoporteRepository soporteRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public SoporteService(SoporteRepository soporteRepository,
                          TicketRepository ticketRepository) {
        this.soporteRepository = soporteRepository;
        this.ticketRepository  = ticketRepository;
    }

    public List<Soporte> findAll() {
        return soporteRepository.findAll();
    }

    public Soporte findById(String soporteId) {
        return soporteRepository.findById(soporteId)
                .orElseThrow(() -> new RuntimeException("Soporte no encontrado con ID: " + soporteId));
    }

    public Soporte create(Soporte soporte) {
        if (soporteRepository.existsById(String.valueOf(soporte.getSoporte()))) {
            throw new RuntimeException("Ya existe un soporte con ID: " + soporte.getSoporte());
        }
        return soporteRepository.save(soporte);
    }

    public void partialUpdate(String soporteId, Soporte soporteRequest) {
        Soporte existing = findById(soporteId);
        if (soporteRequest.getNombreSoporte() != null) {
            existing.setNombreSoporte(soporteRequest.getNombreSoporte());
        }
        if (soporteRequest.getApellidoSoporte() != null) {
            existing.setApellidoSoporte(soporteRequest.getApellidoSoporte());
        }
        soporteRepository.save(existing);
    }

    public void remove(String soporteId) {
        // No permitir eliminar si existen tickets asociados
        if (ticketRepository.existsBySoporte(soporteId)) {
            throw new RuntimeException(
                    "No se puede eliminar el soporte con ID: " + soporteId +
                            " porque tiene tickets asociados"
            );
        }
        if (!soporteRepository.existsById(soporteId)) {
            throw new RuntimeException("Soporte no encontrado con ID: " + soporteId);
        }
        soporteRepository.deleteById(soporteId);
    }
}