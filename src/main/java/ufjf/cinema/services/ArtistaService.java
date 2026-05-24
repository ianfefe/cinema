package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.model.entity.Artista;
import ufjf.cinema.model.entity.Diretor;
import ufjf.cinema.model.repository.ArtistaRepository;
import ufjf.cinema.model.repository.DiretorRepository;


@Service
public class ArtistaService extends CrudServiceBase<Artista, Long> {
    ArtistaRepository artistaRepository;

    public ArtistaService(JpaRepository<Artista, Long> repository) {
        super(repository);
        this.artistaRepository = (ArtistaRepository)repository;
    }

    @Override
    public void validar(Artista artista) {
        validarCampo(artista.getNome(), "nome");
    }
}
