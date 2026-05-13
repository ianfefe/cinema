package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.TipoAssento;

public interface TipoAssentoRepository extends JpaRepository<TipoAssento, Long> {
}
