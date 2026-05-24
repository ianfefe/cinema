package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.model.entity.Diretor;
import ufjf.cinema.model.repository.DiretorRepository;


@Service
public class DiretorService extends CrudServiceBase<Diretor, Long> {
    DiretorRepository diretorRepository;

    public DiretorService(JpaRepository<Diretor, Long> repository) {
        super(repository);
        this.diretorRepository = (DiretorRepository)repository;
    }

    @Override
    public void validar(Diretor diretor) {
        validarCampo(diretor.getNome(), "nome");
    }
}
