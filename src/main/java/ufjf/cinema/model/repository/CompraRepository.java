package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Compra;
import ufjf.cinema.model.entity.Ingresso;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Ingresso> getIngressos(Compra compra);
}
