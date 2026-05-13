package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.TipoIngresso;

public interface TipoIngressoRepository extends JpaRepository<TipoIngresso, Long> {
}
