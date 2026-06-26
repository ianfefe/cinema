package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Artista;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.entity.FilmesArtista;

public interface FilmesArtistaRepository extends JpaRepository<FilmesArtista, Long> {
    boolean existsByFilmeAndArtista(Filme filme, Artista artista);
}
