package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Cliente;
import ufjf.cinema.model.entity.Compra;
import ufjf.cinema.model.entity.Funcionario;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    boolean existsByCliente(Cliente cliente);

    boolean existsByFuncionario(Funcionario funcionario);
}
