package ufjf.cinema.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.*;
import ufjf.cinema.model.repository.*;

import java.util.List;

@Service
public class FilmeService extends CrudServiceBase<Filme, Long> {
    private FilmeRepository filmeRepository;
    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private GenerosFilmeRepository generosFilmeRepository;
    @Autowired
    private FilmesDiretorRepository filmesDiretorRepository;
    @Autowired
    private FilmesArtistaRepository filmesArtistaRepository;
    @Autowired
    private FilmesCinemaRepository filmesCinemaRepository;


    public FilmeService(JpaRepository<Filme, Long> repository) {
        super(repository);
        this.filmeRepository = (FilmeRepository) repository;
    }

    public List<Filme> getFilmesByNome(String nome) {
        return filmeRepository.findFilmeByNome(nome);
    }

    public List<Filme> getFilmesByClassificacaoIndicativa(String classificacaoIndicativa) {
        return filmeRepository.findByClassificacaoIndicativa(classificacaoIndicativa);
    }

    @Override
    public void validar(Filme filme) {
        validarCampo(filme.getNome(), "nome");
        validarCampo(filme.getSinopse(), "sinopse");
        validarCampo(filme.getClassificacaoIndicativa(), "classificacao indicativa");
        validarCampo(filme.getPoster(), "poster");
        if (filme.getDuracao() == null || filme.getDuracao() < 0) {
            throw new RegraNegocioException("Duração inválida");
        }
    }

    @Override
    @Transactional
    public void excluir(Filme filme, Long id) {
        if (sessaoRepository.existsByFilme(filme)) {
            throw new RegraNegocioException("Não é possível excluir o filme: existem sessões vinculadas a ele.");
        }

        List<GenerosFilme> generos = generosFilmeRepository.findByFilme(filme);
        if (generos != null && !generos.isEmpty()) {
            generosFilmeRepository.deleteAll(generos);
        }

        List<FilmesDiretor> diretores = filmesDiretorRepository.findByFilme(filme);
        if (diretores != null && !diretores.isEmpty()) {
            filmesDiretorRepository.deleteAll(diretores);
        }

        List<FilmesArtista> artistas = filmesArtistaRepository.findByFilme(filme);
        if (artistas != null && !artistas.isEmpty()) {
            filmesArtistaRepository.deleteAll(artistas);
        }

        List<FilmesCinema> catalogo = filmesCinemaRepository.findByFilme(filme);
        if (catalogo != null && !catalogo.isEmpty()) {
            filmesCinemaRepository.deleteAll(catalogo);
        }

        super.excluir(filme, id);
    }
}
