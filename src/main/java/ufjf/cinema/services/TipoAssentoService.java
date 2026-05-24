package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
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
        validarCampo(tipoAssento.getTipo(), "tipo de assento");
    }
}
