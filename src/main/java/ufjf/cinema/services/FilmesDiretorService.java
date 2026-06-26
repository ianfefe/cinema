package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.FilmesDiretor;
import ufjf.cinema.model.repository.FilmesDiretorRepository;

@Service
public class FilmesDiretorService extends CrudServiceBase<FilmesDiretor, Long>{
    private FilmesDiretorRepository filmesDiretorRepository;

    public FilmesDiretorService(JpaRepository<FilmesDiretor, Long> repository) {
        super(repository);
        this.filmesDiretorRepository = (FilmesDiretorRepository) repository;
    }

    @Override
    public void validar(FilmesDiretor filmesDiretor) {
        validarEntidade(filmesDiretor.getDiretor(), "diretor");
        validarEntidade(filmesDiretor.getFilme(), "filme");

        if(filmesDiretorRepository.existsByFilmeAndDiretor(filmesDiretor.getFilme(), filmesDiretor.getDiretor())){
            throw new RegraNegocioException("Este artista já esta cadastrado no filme");
        }
    }
}
