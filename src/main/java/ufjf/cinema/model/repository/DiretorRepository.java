package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Diretor;

public interface DiretorRepository extends JpaRepository<Diretor, Long> {
}
