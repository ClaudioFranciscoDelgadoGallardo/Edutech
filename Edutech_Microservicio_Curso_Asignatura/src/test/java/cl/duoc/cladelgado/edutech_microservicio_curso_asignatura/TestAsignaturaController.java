package cl.duoc.cladelgado.edutech_microservicio_curso_asignatura;

import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.controller.AsignaturaController;
import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.domain.Asignatura;
import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.AsignaturaService;
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
class TestAsignaturaController{

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AsignaturaService asignaturaService;

    @InjectMocks
    private AsignaturaController asignaturaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(asignaturaController).build();
    }

    @Test
    void crearAsignatura_OK() throws Exception {
        Asignatura in  = new Asignatura(1L, 1L);
        Asignatura out = new Asignatura(1L, 1L);
        given(asignaturaService.create(any(Asignatura.class))).willReturn(out);

        mockMvc.perform(post("/asignaturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.asignatura").value(1))
                .andExpect(jsonPath("$.curso").value(1));

        then(asignaturaService).should().create(any(Asignatura.class));
    }

    @Test
    void obtenerTodas_OK() throws Exception {
        Asignatura a1 = new Asignatura(1L, 1L);
        Asignatura a2 = new Asignatura(2L, 2L);
        given(asignaturaService.findAll()).willReturn(Arrays.asList(a1, a2));

        mockMvc.perform(get("/asignaturas/allasignaturas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].curso").value(1));

        then(asignaturaService).should().findAll();
    }

    @Test
    void obtenerPorId_OK() throws Exception {
        Asignatura a = new Asignatura(3L, 2L);
        given(asignaturaService.findById(3L)).willReturn(a);

        mockMvc.perform(get("/asignaturas/{id}", 3L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.curso").value(2));

        then(asignaturaService).should().findById(3L);
    }

    @Test
    void actualizarAsignatura_PUT_OK() throws Exception {
        Asignatura req = new Asignatura(1L, 2L);

        mockMvc.perform(put("/asignaturas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        then(asignaturaService).should().partialUpdate(eq(1L), any(Asignatura.class));
    }

    @Test
    void actualizarParcialAsignatura_PATCH_OK() throws Exception {
        Asignatura patch = new Asignatura();
        patch.setCurso(3L);

        mockMvc.perform(patch("/asignaturas/{id}", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patch)))
                .andExpect(status().isOk());

        then(asignaturaService).should().partialUpdate(eq(5L), any(Asignatura.class));
    }

    @Test
    void eliminarAsignatura_OK() throws Exception {
        mockMvc.perform(delete("/asignaturas/{id}", 99L))
                .andExpect(status().isOk());

        then(asignaturaService).should().remove(99L);
    }
}