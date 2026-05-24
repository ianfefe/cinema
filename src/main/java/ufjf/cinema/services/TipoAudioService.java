package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.TipoAudio;
import ufjf.cinema.model.repository.TipoAudioRepository;


@Service
public class TipoAudioService extends CrudServiceBase<TipoAudio, Long> {
    TipoAudioRepository tipoAudioRepository;

    public TipoAudioService(JpaRepository<TipoAudio, Long> repository) {
        super(repository);
        this.tipoAudioRepository = (TipoAudioRepository)repository;
    }

    @Override
    public void validar(TipoAudio tipoAudio) {
        if (tipoAudio.getTipo() == null || tipoAudio.getTipo().isEmpty()) {
            throw new RegraNegocioException("Tipo de audio invalido");
        }
    }
}
