package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Funcionario;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findByCinema(Cinema cinema);

    Optional<Funcionario> findByMatricula(Integer matricula);

    Optional<Funcionario> findByNome(String nome);
}
