package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}
