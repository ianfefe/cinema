 package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>
{
}
