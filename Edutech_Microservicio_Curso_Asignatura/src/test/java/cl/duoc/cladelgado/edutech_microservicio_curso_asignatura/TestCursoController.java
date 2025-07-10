package cl.duoc.cladelgado.edutech_microservicio_curso_asignatura;

import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.controller.CursoController;
import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.domain.Curso;
import cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.CursoService;
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
class TestCursoController {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CursoService cursoService;

    @InjectMocks
    private CursoController cursoController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cursoController).build();
    }

    @Test
    void crearCurso_OK() throws Exception {
        Curso in  = new Curso(1L, "Matemáticas", 12345678L);
        Curso out = new Curso(1L, "Matemáticas", 12345678L);
        given(cursoService.create(any(Curso.class))).willReturn(out);

        mockMvc.perform(post("/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.curso").value(1))
                .andExpect(jsonPath("$.nombreCurso").value("Matemáticas"));

        then(cursoService).should().create(any(Curso.class));
    }

    @Test
    void obtenerTodos_OK() throws Exception {
        Curso c1 = new Curso(1L, "Matemáticas", 111L);
        Curso c2 = new Curso(2L, "Física",      222L);
        given(cursoService.findAll()).willReturn(Arrays.asList(c1, c2));

        mockMvc.perform(get("/cursos/allcursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].nombreCurso").value("Física"));

        then(cursoService).should().findAll();
    }

    @Test
    void obtenerPorId_OK() throws Exception {
        Curso c = new Curso(42L, "Química", 333L);
        given(cursoService.findById(42L)).willReturn(c);

        mockMvc.perform(get("/cursos/{id}", 42L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutUsuario").value(333));

        then(cursoService).should().findById(42L);
    }

    @Test
    void actualizarCurso_PUT_OK() throws Exception {
        Curso req = new Curso(1L, "Biología", 123L);

        mockMvc.perform(put("/cursos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        then(cursoService).should().partialUpdate(eq(1L), any(Curso.class));
    }

    @Test
    void actualizarParcialCurso_PATCH_OK() throws Exception {
        Curso patch = new Curso();
        patch.setNombreCurso("Historia");

        mockMvc.perform(patch("/cursos/{id}", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patch)))
                .andExpect(status().isOk());

        then(cursoService).should().partialUpdate(eq(5L), any(Curso.class));
    }

    @Test
    void eliminarCurso_OK() throws Exception {
        mockMvc.perform(delete("/cursos/{id}", 99L))
                .andExpect(status().isOk());

        then(cursoService).should().remove(99L);
    }
}