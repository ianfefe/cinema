package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
}
