package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.TipoSala;

public interface TipoSalaRepository extends JpaRepository<TipoSala, Long> {
}
