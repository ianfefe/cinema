package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Cinema;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
