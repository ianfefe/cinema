package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.entity.Sessao;
import ufjf.cinema.model.repository.FilmesCinemaRepository;
import ufjf.cinema.model.repository.SessaoRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SessaoService extends CrudServiceBase<Sessao, Long>{
    private final SessaoRepository sessaoRepository;
    private final FilmesCinemaRepository filmesCinemaRepository;

    public SessaoService(JpaRepository<Sessao, Long> repository, FilmesCinemaRepository filmesCinemaRepository) {
        super(repository);
        this.sessaoRepository = (SessaoRepository) repository;
        this.filmesCinemaRepository = filmesCinemaRepository;
    };

    public List<Sessao> getSessaoByFilme(Filme filme) {
        return sessaoRepository.findByFilme(filme);
    }

    @Override
    public void validar(Sessao sessao) {
        validarEntidade(sessao.getTipoImagem(), "tipo de imagem");
        validarEntidade(sessao.getTipoAudio(), "tipo de audio");
        validarEntidade(sessao.getFilme(), "filme");
        validarCampo(sessao.getHorarioInicial(), "horario inicial");
        validarEntidade(sessao.getSala(), "sala");

        if (!filmesCinemaRepository.existsByCinemaAndFilme(sessao.getSala().getCinema(), sessao.getFilme())) {
            throw new RegraNegocioException("Este filme não está associado ao catálogo deste cinema.");
        }

        verificarHorarioFinal(sessao);
        verificarSala(sessao);
        sessao.setPrecoBase(this.getValorSessao(sessao));
    }

    public BigDecimal getValorSessao(Sessao sessao) {

        BigDecimal valor = new BigDecimal ("40.00");

        if(sessao.getTipoImagem().getTipo().equals("VIP")){
            valor = valor.add(new BigDecimal("4"));
        }

        valor = switch (sessao.getSala().getTipoSala().getTipo()) {
            case "LASER" -> valor.add(new BigDecimal("4"));
            case "IMAX" -> valor.multiply(new BigDecimal("1.25"));
            case "VIP" -> valor.multiply(new BigDecimal("2"));
            default -> valor;
        };

        return valor;
    }

    public void verificarHorarioFinal(Sessao sessao){
        sessao.setHorarioFinal(sessao.getHorarioInicial() + (sessao.getFilme().getDuracao()).toString());
    }

    public void verificarSala(Sessao sessao) {
        if(sessaoRepository.existsBySalaAndHorarioInicialBetween(sessao.getSala(), sessao.getHorarioInicial(), sessao.getHorarioFinal())){
            throw new RegraNegocioException("Sala indisponível no horário escolhido");
        };
    }
}
