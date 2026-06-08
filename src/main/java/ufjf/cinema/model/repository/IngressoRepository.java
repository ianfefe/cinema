package ufjf.cinema.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.model.entity.Assento;
import ufjf.cinema.model.entity.Compra;
import ufjf.cinema.model.entity.Ingresso;
import ufjf.cinema.model.entity.Sessao;

import java.util.List;

public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
    List<Ingresso> getIngressosByCompra(Compra compra);

    List<Ingresso> getIngressosBySessao(Sessao sessao);

    boolean existsIngressoBySessaoAndAssento(Sessao sessao, Assento assento);
}
