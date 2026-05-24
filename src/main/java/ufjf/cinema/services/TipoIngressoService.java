package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.TipoIngresso;
import ufjf.cinema.model.repository.TipoIngressoRepository;


@Service
public class TipoIngressoService extends CrudServiceBase<TipoIngresso, Long> {
    TipoIngressoRepository tipoIngressoRepository;

    public TipoIngressoService(JpaRepository<TipoIngresso, Long> repository) {
        super(repository);
        this.tipoIngressoRepository = (TipoIngressoRepository)repository;
    }

    @Override
    public void validar(TipoIngresso tipoIngresso) {
        if (tipoIngresso.getTipo() == null || tipoIngresso.getTipo().isEmpty()) {
            throw new RegraNegocioException("Tipo de ingresso invalido");
        }
    }
}
