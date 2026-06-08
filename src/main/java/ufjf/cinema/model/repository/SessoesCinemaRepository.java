package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.FilmesCinema;
import ufjf.cinema.model.entity.SessoesCinema;

public interface SessoesCinemaRepository extends JpaRepository<SessoesCinema, Long> {
}
