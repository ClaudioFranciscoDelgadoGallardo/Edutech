package cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service;

import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.repository.TicketRepository;
import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.domain.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final RestTemplate restTemplate;
    private final String soporteServiceUrl;

    @Autowired
    public TicketService(
            TicketRepository ticketRepository,
            RestTemplate restTemplate,
            @Value("${soporte.service.url:http://localhost:8082}") String soporteServiceUrl) {
        this.ticketRepository = ticketRepository;
        this.restTemplate = restTemplate;
        this.soporteServiceUrl = soporteServiceUrl;
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket findById(long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));
    }

    public Ticket create(Ticket ticket) {
        // No permitir duplicados de ticket
        if (ticketRepository.existsById(ticket.getTicket())) {
            throw new RuntimeException("Ya existe un ticket con ID: " + ticket.getTicket());
        }

        // Validar existencia del soporte en el microservicio de Soporte
        String url = soporteServiceUrl + "/soportes/" + ticket.getSoporte();
        try {
            restTemplate.getForEntity(url, Object.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RuntimeException("Soporte no encontrado con ID: " + ticket.getSoporte());
        } catch (ResourceAccessException ex) {
            throw new RuntimeException("No se pudo conectar al servicio de Soporte: " + ex.getMessage());
        }

        return ticketRepository.save(ticket);
    }

    public void partialUpdate(long id, Ticket ticketRequest) {
        Ticket existing = findById(id);
        if (ticketRequest.getSoporte() != null) {
            String url = soporteServiceUrl + "/soportes/" + ticketRequest.getSoporte();
            try {
                restTemplate.getForEntity(url, Object.class);
            } catch (HttpClientErrorException.NotFound ex) {
                throw new RuntimeException("Soporte no encontrado con ID: " + ticketRequest.getSoporte());
            } catch (ResourceAccessException ex) {
                throw new RuntimeException("No se pudo conectar al servicio de Soporte: " + ex.getMessage());
            }
            existing.setSoporte(ticketRequest.getSoporte());
        }
        if (ticketRequest.getRutUsuario() != null) existing.setRutUsuario(ticketRequest.getRutUsuario());
        if (ticketRequest.getCausa() != null)      existing.setCausa(ticketRequest.getCausa());
        if (ticketRequest.getCaso() != null)       existing.setCaso(ticketRequest.getCaso());
        if (ticketRequest.getDescripcion() != null)existing.setDescripcion(ticketRequest.getDescripcion());

        ticketRepository.save(existing);
    }

    public void remove(long id) {
        if (!ticketRepository.existsById(id)) {
            throw new RuntimeException("Ticket no encontrado con ID: " + id);
        }
        ticketRepository.deleteById(id);
    }
}