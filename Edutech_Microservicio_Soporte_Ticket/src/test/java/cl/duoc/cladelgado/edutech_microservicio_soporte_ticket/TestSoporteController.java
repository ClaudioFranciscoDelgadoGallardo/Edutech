package cl.duoc.cladelgado.edutech_microservicio_soporte_ticket;

import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.controller.SoporteController;
import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.domain.Soporte;
import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.SoporteService;
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
class TestSoporteController {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private SoporteService soporteService;

    @InjectMocks
    private SoporteController soporteController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(soporteController).build();
    }

    @Test
    void crearSoporte_OK() throws Exception {
        Soporte in  = new Soporte(1L, "Juan", "Perez");
        Soporte out = new Soporte(1L, "Juan", "Perez");
        given(soporteService.create(any(Soporte.class))).willReturn(out);

        mockMvc.perform(post("/soportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.soporte").value(1))
                .andExpect(jsonPath("$.nombreSoporte").value("Juan"));

        then(soporteService).should().create(any(Soporte.class));
    }

    @Test
    void obtenerTodos_OK() throws Exception {
        Soporte s1 = new Soporte(1L, "Ana", "Gonzalez");
        Soporte s2 = new Soporte(2L, "Luis", "Ramirez");
        given(soporteService.findAll()).willReturn(Arrays.asList(s1, s2));

        mockMvc.perform(get("/soportes/allsoportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombreSoporte").value("Ana"));

        then(soporteService).should().findAll();
    }

    @Test
    void obtenerPorId_OK() throws Exception {
        Soporte s = new Soporte(3L, "Pedro", "Lopez");
        given(soporteService.findById(String.valueOf(3L))).willReturn(s);

        mockMvc.perform(get("/soportes/{soporte}", 3L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apellidoSoporte").value("Lopez"));

        then(soporteService).should().findById(String.valueOf(3L));
    }

    @Test
    void actualizarParcialSoporte_OK() throws Exception {
        Soporte patch = new Soporte();
        patch.setApellidoSoporte("NuevoApellido");

        mockMvc.perform(patch("/soportes/{soporte}", "4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patch)))
                .andExpect(status().isOk());

        then(soporteService).should().partialUpdate(eq("4"), any(Soporte.class));
    }

    @Test
    void eliminarSoporte_OK() throws Exception {
        mockMvc.perform(delete("/soportes/{soporte}", 5L))
                .andExpect(status().isOk());

        then(soporteService).should().remove(String.valueOf(5L));
    }
}