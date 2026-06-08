package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.model.entity.SessoesCinema;
import ufjf.cinema.model.repository.SessoesCinemaRepository;

@Service
public class SessoesCinemaService extends CrudServiceBase<SessoesCinema, Long> {
    private SessoesCinemaRepository sessoesCinemaRepository;

    public SessoesCinemaService(JpaRepository<SessoesCinema, Long> repository) {
        super(repository);
        this.sessoesCinemaRepository = (SessoesCinemaRepository) repository;
    }
    @Override
    public void validar(SessoesCinema sessoesCinema) {
        validarEntidade(sessoesCinema.getCinema(), "cinema");
        validarEntidade(sessoesCinema.getSessao(), "sessao");
    }
}
