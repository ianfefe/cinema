package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.FilmesCinema;

public interface SessoesCinemaRepository extends JpaRepository<FilmesCinema, Long> {
}
