package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Ingresso;
import ufjf.cinema.model.repository.IngressoRepository;

import java.util.List;

@Service
public class IngressoService extends CrudServiceBase<Ingresso, Long> {
    private IngressoRepository ingressoRepository;

    public IngressoService(JpaRepository<Ingresso, Long> repository) {
        super(repository);
        this.ingressoRepository = (IngressoRepository) repository;
    }

    @Override
    public void validar(Ingresso ingresso){
        validarEntidade(ingresso.getAssento(), "assento");
        validarEntidade(ingresso.getSessao(), "sessao");

        if (ingresso.getAssento().getSala() != ingresso.getSessao().getSala()) {
            throw new RegraNegocioException("Este assento não pertence à sala desta sessão.");
        }else{
            if(lugarDisponivel(ingresso)){
                validarEntidade(ingresso.getCompra(), "compra");
                validarEntidade(ingresso.getTipoIngresso(), "tipo de ingresso");
            }else {
                throw new RegraNegocioException("O assento escolhido está indisponivel");
            }
        }
    }

    public boolean lugarDisponivel(Ingresso ingresso){
        return !ingressoRepository.existsIngressoBySessaoAndAssento(ingresso.getSessao(), ingresso.getAssento());
    }

    public List<Ingresso> getIngressosByCompra(Ingresso ingresso){
        return ingressoRepository.getIngressosByCompra(ingresso.getCompra());
    }
}