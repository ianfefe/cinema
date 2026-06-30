package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.GenerosFilme;
import ufjf.cinema.model.repository.GenerosFilmeRepository;

@Service
public class GenerosFilmeService extends CrudServiceBase<GenerosFilme, Long> {
    private final GenerosFilmeRepository generosFilmeRepository;

    public GenerosFilmeService(JpaRepository<GenerosFilme, Long> repository) {
        super(repository);
        this.generosFilmeRepository = (GenerosFilmeRepository) repository;
    }

    @Override
    public void validar(GenerosFilme generosFilme) {
        validarEntidade(generosFilme.getGenero(), "genero");
        validarEntidade(generosFilme.getFilme(), "filme");

        if (generosFilmeRepository.existsByGeneroAndFilme(generosFilme.getGenero(), generosFilme.getFilme())) {
            throw new RegraNegocioException("Este genero já esta cadastrado no filme");
        }
    }
}
