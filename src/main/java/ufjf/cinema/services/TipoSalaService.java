package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.model.entity.TipoSala;
import ufjf.cinema.model.repository.TipoSalaRepository;


@Service
public class TipoSalaService extends CrudServiceBase<TipoSala, Long> {
    TipoSalaRepository tipoSalaRepository;

    public TipoSalaService(JpaRepository<TipoSala, Long> repository) {
        super(repository);
        this.tipoSalaRepository = (TipoSalaRepository)repository;
    }

    @Override
    public void validar(TipoSala tipoSala) {
        validarCampo(tipoSala.getTipo(), "tipo de sala");
    }
}
