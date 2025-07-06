package cl.duoc.cladelgado.edutech_microservico_usuario;

import cl.duoc.cladelgado.edutech_microservico_usuario.controller.UserController;
import cl.duoc.cladelgado.edutech_microservico_usuario.service.UserService;
import cl.duoc.cladelgado.edutech_microservico_usuario.service.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    void crearUsuario_OK() throws Exception {
        User in = new User(1L, "K", "Juan", "Perez", "juan@example.com", "USER", "pass");
        User out = new User(1L, "K", "Juan", "Perez", "juan@example.com", "USER", "encrypted");
        given(userService.create(any(User.class))).willReturn(out);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rut").value(1))
                .andExpect(jsonPath("$.correo").value("juan@example.com"));

        then(userService).should().create(any(User.class));
    }

    @Test
    void obtenerTodos_OK() throws Exception {
        User u1 = new User(1L, "K", "Ana", "Gonzalez", "ana@example.com", "USER", "x");
        User u2 = new User(2L, "L", "Luis", "Ramirez", "luis@example.com", "USER", "x");
        given(userService.findAll()).willReturn(Arrays.asList(u1, u2));

        mockMvc.perform(get("/users/allusers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                // opcional: verificar algunos campos
                .andExpect(jsonPath("$[0].nombre").value("Ana"));

        then(userService).should().findAll();
    }

    @Test
    void obtenerPorRut_OK() throws Exception {
        User u = new User(42L, "K", "Pedro", "Lopez", "pedro@example.com", "USER", "x");
        given(userService.findByRut(42L)).willReturn(u);

        mockMvc.perform(get("/users/{rut}", 42))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apellido").value("Lopez"));

        then(userService).should().findByRut(42L);
    }

    @Test
    void actualizarUsuario_PARTE_OK() throws Exception {
        User patch = new User();
        patch.setCorreo("nuevo@example.com");

        mockMvc.perform(put("/users/{rut}", 12345678L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patch)))
                .andExpect(status().isOk());

        // eq para el long y any(User.class) para el body
        then(userService).should().partialUpdate(eq(12345678L), any(User.class));
    }

    @Test
    void eliminarUsuario_OK() throws Exception {
        mockMvc.perform(delete("/users/{rut}", 99L))
                .andExpect(status().isOk());

        then(userService).should().remove(99L);
    }

    @Test
    void actualizarPassword_OK() throws Exception {
        // enviamos sólo el JSON { "password": "nuevaClave123" }
        mockMvc.perform(put("/users/{rut}/password", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"nuevaClave123\"}"))
                .andExpect(status().isOk());

        then(userService).should().updatePassword(eq(5L), eq("nuevaClave123"));
    }
}

