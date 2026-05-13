package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
}
