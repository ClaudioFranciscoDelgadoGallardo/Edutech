package cl.duoc.cladelgado.edutech_microservico_usuario.Repository;

import cl.duoc.cladelgado.edutech_microservico_usuario.service.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
