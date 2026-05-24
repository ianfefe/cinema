package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.model.entity.Genero;
import ufjf.cinema.model.repository.GeneroRepository;


@Service
public class GeneroService extends CrudServiceBase<Genero, Long> {
    GeneroRepository generoRepository;

    public GeneroService(JpaRepository<Genero, Long> repository) {
        super(repository);
        this.generoRepository = (GeneroRepository)repository;
    }

    @Override
    public void validar(Genero genero) {
        validarCampo(genero.getGenero(), "genero");
    }
}
