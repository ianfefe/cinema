package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.FilmesArtista;

public interface FilmesArtistaRepository extends JpaRepository<FilmesArtista, Long> {
}
