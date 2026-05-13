package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long> {
}
