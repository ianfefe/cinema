package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Assento;

public interface AssentoRepository extends JpaRepository<Assento, Long> {
}
