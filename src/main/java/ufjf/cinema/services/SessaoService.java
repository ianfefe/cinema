package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.entity.Sessao;
import ufjf.cinema.model.repository.SessaoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SessaoService extends CrudServiceBase<Sessao, Long>{
    private SessaoRepository sessaoRepository;

    public SessaoService(JpaRepository<Sessao, Long> repository) {
        super(repository);
        this.sessaoRepository = (SessaoRepository) repository;
    };

    public List<Sessao> getSessaoByFilme(Optional<Filme> filme) {
        return sessaoRepository.findByFilme(filme);
    }

    @Override
    public void validar(Sessao sessao) {
        if (sessao.getTipoAudio() == null || sessao.getTipoAudio().getTipo().trim().isEmpty()) {
            throw new RegraNegocioException("Sessão com tipo de aúdio inválido");
        }
        if (sessao.getTipoImagem() == null || sessao.getTipoImagem().getTipo().trim().isEmpty()) {
            throw new RegraNegocioException("Sessão com tipo de imagem inválido");
        }
        if (sessao.getFilme() == null || sessao.getFilme().getId() == null ||sessao.getFilme().getId() == 0) {
            throw new RegraNegocioException("Filme inválido");
        }

//        if (sessao.getHorario() == null || sessao.getHorario() > 0 ) {
//            throw new RegraNegocioException("Horário inválido");
//        }
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
        sessao.setHorarioInicial(sessao.getHorarioInicial() + (sessao.getFilme().getDuracao()).toString());
    }

    public void verificarSala(Sessao sessao) {
        if (sessao.getSala() == null || sessao.getSala().getId() == null || sessao.getSala().getId() == 0) {
            throw new RegraNegocioException("Sala inválida");
        }
        if(sessaoRepository.existsBySalaAndHorarioInicialBetween(sessao.getSala(), sessao.getHorarioInicial(), sessao.getHorarioFinal())){
            throw new RegraNegocioException("Sala indisponível no horário escolhido");
        };
    }

}
