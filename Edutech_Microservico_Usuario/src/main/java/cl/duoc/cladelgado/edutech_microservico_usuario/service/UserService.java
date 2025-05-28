package cl.duoc.cladelgado.edutech_microservico_usuario.service;
import cl.duoc.cladelgado.edutech_microservico_usuario.service.domain.User;
import cl.duoc.cladelgado.edutech_microservico_usuario.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public User findById(String id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public User create(User u) {
        if (repo.existsByUsername(u.getUsername()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username ya existe");
        if (repo.existsByEmail(u.getEmail()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ya registrado");
        return repo.save(u);
    }

    public User update(String id, User u) {
        User existing = findById(id);
        existing.setNombreCompleto(u.getNombreCompleto());
        existing.setEmail(u.getEmail());
        existing.setRole(u.getRole());
        return repo.save(existing);
    }

    public void delete(String id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        repo.deleteById(id);
    }
}
