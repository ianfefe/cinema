package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
}
