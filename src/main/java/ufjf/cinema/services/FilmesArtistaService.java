package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.model.entity.FilmesArtista;
import ufjf.cinema.model.repository.FilmesArtistaRepository;

@Service
public class FilmesArtistaService extends CrudServiceBase<FilmesArtista, Long>{
    private FilmesArtistaRepository filmesArtistaRepository;

    public FilmesArtistaService(JpaRepository<FilmesArtista, Long> repository) {
        super(repository);
        this.filmesArtistaRepository = (FilmesArtistaRepository) repository;
    }

    @Override
    public void validar(FilmesArtista filmesArtista) {
        validarEntidade(filmesArtista.getArtista(), "artista");
        validarEntidade(filmesArtista.getFilme(), "filme");
    }
}
