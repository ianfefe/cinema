package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.enums.ClassificacaoIndicativaEnum;
import ufjf.cinema.model.repository.FilmeRepository;

import java.util.List;

@Service
public class FilmeService extends CrudServiceBase<Filme, Long> {
    private FilmeRepository filmeRepository;

    public FilmeService(JpaRepository<Filme, Long> repository) {
        super(repository);
        this.filmeRepository = (FilmeRepository) repository;
    }

    public List<Filme> getFilmesByNome(String nome) {
        return filmeRepository.findFilmeByNome(nome);
    }

    public List<Filme> getFilmesByClassificacaoIndicativa(ClassificacaoIndicativaEnum classificacaoIndicativa) {
        return filmeRepository.findByClassificacaoIndicativa(classificacaoIndicativa);
    }

    @Override
    public void validar(Filme filme) {
        validarCampo(filme.getNome(), "nome");
        validarCampo(filme.getSinopse(), "sinopse");
        validarCampo(filme.getPoster(), "poster");
        if (filme.getDuracao() == null || filme.getDuracao() < 0) {
            throw new RegraNegocioException("Duração inválida");
        }
        if (filme.getClassificacaoIndicativa() == null ) {
            throw new RegraNegocioException("Classificação indicativa inválida");
        }


    }

}
