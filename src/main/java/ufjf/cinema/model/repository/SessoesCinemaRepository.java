package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Sessao;
import ufjf.cinema.model.entity.SessoesCinema;

public interface SessoesCinemaRepository extends JpaRepository<SessoesCinema, Long> {
    boolean existsBySessaoAndCinema(Sessao sessao, Cinema cinema);
}
