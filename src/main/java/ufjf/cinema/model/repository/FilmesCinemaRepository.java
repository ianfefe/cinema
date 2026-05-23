package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.FilmesCinema;

public interface FilmesCinemaRepository extends JpaRepository<FilmesCinema, Long> {
}
