package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Long>
{
}
