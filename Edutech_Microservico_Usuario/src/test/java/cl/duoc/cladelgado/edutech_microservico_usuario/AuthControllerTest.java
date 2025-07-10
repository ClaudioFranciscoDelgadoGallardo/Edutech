package cl.duoc.cladelgado.edutech_microservico_usuario;

import cl.duoc.cladelgado.edutech_microservico_usuario.controller.AuthController;
import cl.duoc.cladelgado.edutech_microservico_usuario.controller.dto.AuthUserDTO;
import cl.duoc.cladelgado.edutech_microservico_usuario.controller.dto.LoginRequest;
import cl.duoc.cladelgado.edutech_microservico_usuario.controller.dto.UserDTO;
import cl.duoc.cladelgado.edutech_microservico_usuario.service.AuthService;
import cl.duoc.cladelgado.edutech_microservico_usuario.controller.config.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AuthService authService;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void login_Success_OK() throws Exception {
        String correo = "juan@example.com";
        String rawPassword = "pass123";
        String encodedPassword = "encoded!";

        UserDTO userDTO = new UserDTO(correo, encodedPassword, "USER");
        given(authService.getUser(correo)).willReturn(userDTO);
        given(passwordEncoder.matches(rawPassword, encodedPassword)).willReturn(true);
        given(jwtUtil.generateToken(correo, "USER")).willReturn("jwt-token-xyz");

        LoginRequest req = new LoginRequest(correo, rawPassword);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(correo))
                .andExpect(jsonPath("$.token").value("jwt-token-xyz"));

        then(authService).should().getUser(correo);
        then(passwordEncoder).should().matches(rawPassword, encodedPassword);
        then(jwtUtil).should().generateToken(correo, "USER");
    }

    @Test
    void login_EmailNotFound_Unauthorized() throws Exception {
        String correo = "noexist@example.com";
        given(authService.getUser(correo)).willReturn(null);

        LoginRequest req = new LoginRequest(correo, "any");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Email not found"));

        then(authService).should().getUser(correo);
        then(passwordEncoder).shouldHaveNoInteractions();
        then(jwtUtil).shouldHaveNoInteractions();
    }

    @Test
    void login_WrongPassword_Unauthorized() throws Exception {
        String correo = "juan@example.com";
        given(authService.getUser(correo))
                .willReturn(new UserDTO(correo, "encoded!", "ADMIN"));
        given(passwordEncoder.matches("bad", "encoded!")).willReturn(false);

        LoginRequest req = new LoginRequest(correo, "bad");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Password not correct"));

        then(authService).should().getUser(correo);
        then(passwordEncoder).should().matches("bad", "encoded!");
        then(jwtUtil).shouldHaveNoInteractions();
    }

    @Test
    void ping_ReturnsPong() throws Exception {
        mockMvc.perform(get("/auth/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("pong"));
    }

    @Test
    void pingUserService_ReturnsBody() throws Exception {
        given(authService.pingUserService()).willReturn("Microservicio Usuario activo");

        mockMvc.perform(get("/auth/ping-user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Microservicio Usuario activo"));

        then(authService).should().pingUserService();
    }
}