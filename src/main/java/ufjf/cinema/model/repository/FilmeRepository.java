package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Filme;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    List<Filme> findFilmeByNome(String nome);

    List<Filme> findByClassificacaoIndicativa(String classificacaoIndicativa);
}
