package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.entity.Sala;
import ufjf.cinema.model.entity.Sessao;

import java.util.List;
import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    List<Sessao> findByFilme(Filme filme);

    boolean existsBySalaAndHorarioInicialBetween(Sala sala, String inicio, String fim);
}
