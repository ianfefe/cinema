package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.entity.Genero;
import ufjf.cinema.model.entity.GenerosFilme;

import java.util.List;

public interface GenerosFilmeRepository extends JpaRepository<GenerosFilme, Long> {
    boolean existsByGeneroAndFilme(Genero genero, Filme filme);

    List<GenerosFilme> findByFilme(Filme filme);

    boolean existsByFilme(Filme filme);
}
