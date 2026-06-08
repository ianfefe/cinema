package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Compra;
import ufjf.cinema.model.entity.Ingresso;
import ufjf.cinema.model.repository.CompraRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CompraService extends CrudServiceBase<Compra, Long>{
    private CompraRepository compraRepository;

    public CompraService(JpaRepository<Compra, Long> repository) {
        super(repository);
        this.compraRepository = (CompraRepository) repository;
    }

    @Override
    public void validar (Compra compra){
        validarCampo(compra.getDataHora(),  "dataHora");
        validarCampo(compra.getFormaPagamento(),  "formaPagamento");
        validarEntidade(compra.getCinema(), "cinema");
        validarEntidade(compra.getCliente(), "cliente");
        validarEntidade(compra.getFuncionario(), "funcionario");

        if (compra.getIngressos() != null && !compra.getIngressos().isEmpty()) {
            for (Ingresso ingresso : compra.getIngressos()) {
                Long idCinemaIngresso = ingresso.getSessao().getSala().getCinema().getId();
                Long idCinemaCompra = compra.getCinema().getId();

                if (!idCinemaIngresso.equals(idCinemaCompra)) {
                    throw new RegraNegocioException("Existem ingressos que não pertencem ao cinema selecionado nesta compra.");
                }
            }
        } else {
            throw new RegraNegocioException("Uma compra deve conter pelo menos um ingresso.");
        }

        compra.setTotal(this.getValorCompra(compra));
    }

    public List<Ingresso> getIngressosByCompra(Compra  compra){
        return compraRepository.getIngressos(compra);
    }

    public BigDecimal getValorCompra(Compra compra) {

        BigDecimal valor = new BigDecimal ("0.00");

        List<Ingresso> ingressos = this.getIngressosByCompra(compra);

        if(ingressos.isEmpty()){
            throw new RegraNegocioException("A compra não possui nenhum ingresso");
        }

        for (Ingresso ingresso : ingressos) {
            BigDecimal valorIngressoSessao = ingresso.getSessao().getPrecoBase();

            if (ingresso.getTipoIngresso().getTipo().equals("MEIA")){
                valor = valor.add(((valorIngressoSessao.divide(new BigDecimal("2.0")))));
            }else if (ingresso.getTipoIngresso().getTipo().equals("INTEIRA")){
                valor = valor.add(valorIngressoSessao);
            }
        }
        return valor;
    }
}
