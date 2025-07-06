package cl.duoc.cladelgado.edutech_microservico_usuario.service;
import cl.duoc.cladelgado.edutech_microservico_usuario.service.domain.User;
import cl.duoc.cladelgado.edutech_microservico_usuario.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByRut(long rut) {
        return userRepository.findById(rut)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con rut: " + rut));
    }

    public User findByCorreo(String correo) {
        User user = userRepository.findByCorreoIgnoreCase(correo);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado con correo: " + correo);
        }
        return user;
    }

    public boolean existsByRut(Number rut) {
        return userRepository.existsById(rut);
    }

    public boolean existsByCorreo(String correo) {
        return userRepository.existsByCorreoIgnoreCase(correo);
    }

    public User create(User user) {
        if (existsByRut(user.getRut())) {
            throw new RuntimeException("Ya existe un usuario con ese RUT");
        }
        if (existsByCorreo(user.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        return userRepository.save(user);
    }


    public void partialUpdate(long rut, User userRequest) {
        User user = findByRut(rut);

        if (userRequest.getCorreo() != null &&
                !user.getCorreo().equalsIgnoreCase(userRequest.getCorreo()) &&
                existsByCorreo(userRequest.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }

        if (userRequest.getCorreo() != null) {
            user.setCorreo(userRequest.getCorreo());
        }
        if (userRequest.getNombre() != null) {
            user.setNombre(userRequest.getNombre());
        }
        if (userRequest.getApellido() != null) {
            user.setApellido(userRequest.getApellido());
        }
        if (userRequest.getRole() != null) {
            user.setRole(userRequest.getRole());
        }

        userRepository.save(user);
    }

    public void remove(long rut) {
        if (!existsByRut(rut)) {
            throw new RuntimeException("Usuario no encontrado con rut: " + rut);
        }
        userRepository.deleteById(rut);
    }

    public void updatePassword(long rut, String newPassword) {
        User user = findByRut(rut);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}