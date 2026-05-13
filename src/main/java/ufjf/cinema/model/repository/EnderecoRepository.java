package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>
{
}
