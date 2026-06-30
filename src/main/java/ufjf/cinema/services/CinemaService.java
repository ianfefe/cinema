package ufjf.cinema.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.repository.CinemaRepository;
import ufjf.cinema.model.repository.FuncionarioRepository;
import ufjf.cinema.model.repository.SalaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaService extends CrudServiceBase<Cinema, Long> {
    private final CinemaRepository cinemaRepository;
    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public CinemaService(JpaRepository<Cinema, Long> repository) {
        super(repository);
        this.cinemaRepository = (CinemaRepository) repository;
    }

    public Optional<Cinema> getCinemaByNome(String nome) {
        return cinemaRepository.findByNome(nome);
    }

    public List<Cinema> getCinemaByCidade(String cidade) {
        return cinemaRepository.findByEndereco_Cidade(cidade);
    }

    @Override
    public void validar(Cinema cinema) {
        validarCampo(cinema.getNome(), "nome");
        validarEntidade(cinema.getEndereco(), "endereco");
    }

    @Override
    public void excluir(Cinema cinema, Long id) {
        if (salaRepository.existsByCinema(cinema)) {
            throw new RegraNegocioException("Não é possível excluir o cinema: existem salas registadas nele.");
        }
        if (!funcionarioRepository.findByCinema(cinema).isEmpty()) {
            throw new RegraNegocioException("Não é possível excluir o cinema: existem funcionários vinculados a ele.");
        }
        super.excluir(cinema, id);
    }
}
