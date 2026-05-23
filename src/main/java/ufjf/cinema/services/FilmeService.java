package ufjf.cinema.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.repository.FilmeRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FilmeService {
    private FilmeRepository filmeRepository;

    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    public List<Filme> getFilmes() {
        return filmeRepository.findAll();
    }

    public Optional<Filme> getFilmeById(Long id) {
        return filmeRepository.findById(id);
    }

    public Optional<Filme> getFilmeByNome(String nome) {
        return filmeRepository.findFilmeByNome(nome);
    }

    @Transactional
    public Filme salvar(Filme filme) {
        validar(filme);
        return filmeRepository.save(filme);
    }

    @Transactional
    public void excluir(Filme filme) {
        Objects.requireNonNull(filme.getId());
        filmeRepository.delete(filme);
    }

    public void validar(Filme filme) {
        if (filme.getNome() == null || filme.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Título inválido");
        }
        if (filme.getSinopse() == null || filme.getSinopse().trim().isEmpty()) {
            throw new RegraNegocioException("Sinopse inválida");
        }
        if (filme.getPoster() == null || filme.getPoster().trim().isEmpty()) {
            throw new RegraNegocioException("Filme sem poster");
        }
        if (filme.getDuracao() == null || filme.getDuracao() < 0) {
            throw new RegraNegocioException("Duração inválida");
        }
        if (filme.getClassificaoIndicativa() == null ) {
            throw new RegraNegocioException("Classificação indicativa inválida");
        }
    }

}
