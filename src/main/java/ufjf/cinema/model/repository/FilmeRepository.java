package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Filme;

import java.util.Optional;

public interface FilmeRepository extends JpaRepository<Filme, Long>
{
    Optional<Filme> findByNome(String nome);
}
