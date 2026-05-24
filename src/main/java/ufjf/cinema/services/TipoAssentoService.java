package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.TipoAssento;
import ufjf.cinema.model.repository.TipoAssentoRepository;


@Service
public class TipoAssentoService extends CrudServiceBase<TipoAssento, Long> {
    TipoAssentoRepository tipoAssentoRepository;

    public TipoAssentoService(JpaRepository<TipoAssento, Long> repository) {
        super(repository);
        this.tipoAssentoRepository = (TipoAssentoRepository)repository;
    }

    @Override
    public void validar(TipoAssento tipoAssento) {
        if (tipoAssento.getTipo() == null || tipoAssento.getTipo().isEmpty()) {
            throw new RegraNegocioException("Tipo de assento invalido");
        }
    }
}
