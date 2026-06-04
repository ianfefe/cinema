package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.model.entity.FilmesCinema;
import ufjf.cinema.model.repository.FilmesCinemaRepository;

@Service
public class FilmesCinemaService extends CrudServiceBase<FilmesCinema, Long>{
    private FilmesCinemaRepository filmesCinemaRepository;

    public FilmesCinemaService(JpaRepository<FilmesCinema, Long> repository) {
        super(repository);
        this.filmesCinemaRepository = (FilmesCinemaRepository) repository;
    }

    @Override
    public void validar(FilmesCinema filmesCinema) {
        validarEntidade(filmesCinema.getCinema(), "cinema");
        validarEntidade(filmesCinema.getFilme(), "filme");
    }
}
