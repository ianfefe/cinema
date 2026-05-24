package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.model.entity.TipoImagem;
import ufjf.cinema.model.repository.TipoImagemRepository;


@Service
public class TipoImagemService extends CrudServiceBase<TipoImagem, Long> {
    TipoImagemRepository tipoImagemRepository;

    public TipoImagemService(JpaRepository<TipoImagem, Long> repository) {
        super(repository);
        this.tipoImagemRepository = (TipoImagemRepository)repository;
    }

    @Override
    public void validar(TipoImagem tipoImagem) {
        validarCampo(tipoImagem.getTipo(), "tipo de imagem");
    }
}
