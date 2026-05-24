package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Cinema;

import java.util.List;
import java.util.Optional;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    List<Cinema> findByEndereco_Cidade(String enderecoCidade);

    Optional<Cinema> findByNome(String nome);
}
