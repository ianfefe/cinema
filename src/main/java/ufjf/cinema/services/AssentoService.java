package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.model.entity.Assento;
import ufjf.cinema.model.repository.AssentoRepository;

@Service
public class AssentoService extends CrudServiceBase<Assento, Long> {
    private AssentoRepository assentoRepository;

    public AssentoService(JpaRepository<Assento, Long> repository) {
        super(repository);
        this.assentoRepository = (AssentoRepository) repository;
    }

    @Override
    public void validar(Assento assento) {
        validarEntidade(assento.getTipoAssento(), "tipoAssento");
        validarCampo(assento.getPosicao(),  "posicao");
    }
}
