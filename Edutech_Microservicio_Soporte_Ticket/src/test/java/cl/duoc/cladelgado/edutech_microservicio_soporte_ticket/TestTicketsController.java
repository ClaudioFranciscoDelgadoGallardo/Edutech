package cl.duoc.cladelgado.edutech_microservicio_soporte_ticket;

import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.controller.TicketController;
import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.domain.Ticket;
import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TestTicketsController {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    @Test
    void crearTicket_OK() throws Exception {
        Ticket in  = new Ticket(1L, "1", 12345678L, "Titulo", "Caso", "Descripcion");
        Ticket out = new Ticket(1L, "1", 12345678L, "Titulo", "Caso", "Descripcion");
        given(ticketService.create(any(Ticket.class))).willReturn(out);

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket").value(1))
                .andExpect(jsonPath("$.soporte").value("1"))
                .andExpect(jsonPath("$.rutUsuario").value(12345678));

        then(ticketService).should().create(any(Ticket.class));
    }

    @Test
    void obtenerTodos_OK() throws Exception {
        Ticket t1 = new Ticket(1L, "1", 111L, "T1", "C1", "D1");
        Ticket t2 = new Ticket(2L, "2", 222L, "T2", "C2", "D2");
        given(ticketService.findAll()).willReturn(Arrays.asList(t1, t2));

        mockMvc.perform(get("/tickets/alltickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].caso").value("C2"));

        then(ticketService).should().findAll();
    }

    @Test
    void obtenerPorId_OK() throws Exception {
        Ticket t = new Ticket(42L, "1", 333L, "T42", "C42", "D42");
        given(ticketService.findById(42L)).willReturn(t);

        mockMvc.perform(get("/tickets/{id}", 42L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("D42"));

        then(ticketService).should().findById(42L);
    }

    @Test
    void actualizarParcialTicket_OK() throws Exception {
        Ticket patch = new Ticket();
        patch.setDescripcion("NuevoDesc");

        mockMvc.perform(patch("/tickets/{id}", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patch)))
                .andExpect(status().isOk());

        then(ticketService).should().partialUpdate(eq(5L), any(Ticket.class));
    }

    @Test
    void eliminarTicket_OK() throws Exception {
        mockMvc.perform(delete("/tickets/{id}", 99L))
                .andExpect(status().isOk());

        then(ticketService).should().remove(99L);
    }
}