package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.TipoImagem;

public interface TipoImagemRepository extends JpaRepository<TipoImagem, Long> {
}
