package cl.duoc.cladelgado.edutech_microservico_usuario.Repository;

import cl.duoc.cladelgado.edutech_microservico_usuario.service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Number> {
    User findByCorreoIgnoreCase(String correo);
    boolean existsByCorreoIgnoreCase(String correo);
}
