package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Diretor;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.entity.FilmesDiretor;

public interface FilmesDiretorRepository extends JpaRepository<FilmesDiretor, Long> {
    boolean existsByFilmeAndDiretor(Filme filme, Diretor diretor);
}
