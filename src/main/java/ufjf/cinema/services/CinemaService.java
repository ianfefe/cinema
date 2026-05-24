package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.repository.CinemaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaService extends CrudServiceBase<Cinema, Long>{
    CinemaRepository cinemaRepository;
    private EnderecoService enderecoService;

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
        if (cinema.getNome() == null || cinema.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Nome inválido");
        }
        enderecoService.validar(cinema.getEndereco());
    }
}
