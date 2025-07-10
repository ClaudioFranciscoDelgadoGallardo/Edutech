package cl.duoc.cladelgado.edutech_microservico_usuario.service;

import cl.duoc.cladelgado.edutech_microservico_usuario.controller.dto.UserDTO;
import cl.duoc.cladelgado.edutech_microservico_usuario.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final UserService userService;
    private final RestTemplate restTemplate;

    @Autowired
    public AuthService(UserService userService,
                       RestTemplate restTemplate) {
        this.userService   = userService;
        this.restTemplate  = restTemplate;
    }

    public UserDTO getUser(String email) {
        User u = userService.findByCorreo(email);
        return new UserDTO(u.getCorreo(), u.getPassword(), u.getRole());
    }

    public String pingUserService() {
        return restTemplate.getForObject("http://localhost:8081/ping", String.class);
    }
}
