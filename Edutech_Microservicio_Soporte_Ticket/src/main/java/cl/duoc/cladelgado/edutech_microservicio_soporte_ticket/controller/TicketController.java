package cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.controller;

import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.domain.Ticket;
import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;


    @PostMapping
    public Ticket crearTicket(@RequestBody Ticket ticket) {
        return ticketService.create(ticket);
    }

    @GetMapping("/alltickets")
    public List<Ticket> obtenerTodos() {
        return ticketService.findAll();
    }

    /** READ ONE */
    @GetMapping("/{ticket}")
    public Ticket obtenerPorTicket(@PathVariable long ticket) {
        return ticketService.findById(ticket);
    }

    /** FULL UPDATE */
    @PutMapping("/{ticket}")
    public void actualizarTicket(@PathVariable long ticket, @RequestBody Ticket ticketRequest) {
        ticketService.partialUpdate(ticket, ticketRequest);
    }

    /** PARTIAL UPDATE */
    @PatchMapping("/{ticket}")
    public void actualizarParcialTicket(@PathVariable long ticket, @RequestBody Ticket ticketRequest) {
        ticketService.partialUpdate(ticket, ticketRequest);
    }

    /** DELETE */
    @DeleteMapping("/{ticket}")
    public void eliminarTicket(@PathVariable long ticket) {
        ticketService.remove(ticket);
    }
}