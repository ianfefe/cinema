package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Sala;
import ufjf.cinema.model.repository.SalaRepository;

@Service
public class SalaService extends CrudServiceBase<Sala, Long>{
    private SalaRepository salaRepository;

    public SalaService(JpaRepository<Sala, Long> repository) {
        super(repository);
        this.salaRepository = (SalaRepository) repository;
    }

    @Override
    public void validar(Sala sala) {
        validarEntidade(sala.getCinema(), "cinema");
        if(sala.getNumeroSala() == null || sala.getNumeroSala()  < 1){
            throw new RegraNegocioException("Numero de sala invalido");
        }
        validarEntidade(sala.getTipoSala(), "tipo de sala");
    }
}
