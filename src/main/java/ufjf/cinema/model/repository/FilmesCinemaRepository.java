package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.entity.FilmesCinema;

import java.util.List;

public interface FilmesCinemaRepository extends JpaRepository<FilmesCinema, Long> {
    boolean existsByCinemaAndFilme(Cinema cinema, Filme filme);

    List<FilmesCinema> findByFilme(Filme filme);
}
