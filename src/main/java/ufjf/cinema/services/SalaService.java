package ufjf.cinema.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Sala;
import ufjf.cinema.model.repository.SalaRepository;
import ufjf.cinema.model.repository.SessaoRepository;

@Service
public class SalaService extends CrudServiceBase<Sala, Long> {
    private final SalaRepository salaRepository;
    @Autowired
    private SessaoRepository sessaoRepository;

    public SalaService(JpaRepository<Sala, Long> repository) {
        super(repository);
        this.salaRepository = (SalaRepository) repository;
    }

    @Override
    public void validar(Sala sala) {
        validarEntidade(sala.getCinema(), "cinema");
        if (sala.getNumeroSala() == null || sala.getNumeroSala() < 1) {
            throw new RegraNegocioException("Numero de sala invalido");
        }
        validarEntidade(sala.getTipoSala(), "tipo de sala");
    }

    @Override
    public void excluir(Sala sala, Long id) {
        if (sessaoRepository.existsBySala(sala)) {
            throw new RegraNegocioException("Não é possível excluir esta sala: existem sessões agendadas para ela.");
        }
        super.excluir(sala, id);
    }
}
