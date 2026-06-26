package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Assento;
import ufjf.cinema.model.entity.Sala;

public interface AssentoRepository extends JpaRepository<Assento, Long> {
    boolean existsByPosicaoAndSala(String posicao, Sala sala);
}
