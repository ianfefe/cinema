package ufjf.cinema.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Sala;
import ufjf.cinema.model.entity.Sessao;
import ufjf.cinema.model.entity.TipoImagem;
import ufjf.cinema.model.entity.TipoSala;
import ufjf.cinema.model.repository.SessaoRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @InjectMocks
    private SessaoService sessaoService;

    @Test
    public void deveLancarErroAoTentarCadastrarSessaoEmSalaOcupada() {
        // Cenário
        Sessao novaSessao = new Sessao();
        novaSessao.setSala(new Sala());
        novaSessao.setHorarioInicial("2026-06-25T19:00:00");
        novaSessao.setHorarioFinal("2026-06-25T21:00:00");

        // Simulação (Stubbing) corrigida
        when(sessaoRepository.existsBySalaAndHorarioInicialBetween(any(), any(), any()))
                .thenReturn(true);

        // Execução e Verificação
        assertThrows(RegraNegocioException.class, () -> {
            sessaoService.verificarSala(novaSessao);
        });
    }

    @Test
    public void deveCalcularPrecoDobradoParaSalaVIP() {
        // Cenário: Preço base padrão (40.00) * 2 para Sala VIP = 80.00
        TipoImagem imagemNormal = new TipoImagem();
        imagemNormal.setTipo("2D");

        TipoSala salaVip = new TipoSala();
        salaVip.setTipo("VIP");

        Sala sala = new Sala();
        sala.setTipoSala(salaVip);

        Sessao sessao = new Sessao();
        sessao.setTipoImagem(imagemNormal);
        sessao.setSala(sala);

        // Execução
        BigDecimal precoCalculado = sessaoService.getValorSessao(sessao);

        // Verificação
        assertEquals(0, new BigDecimal("80.00").compareTo(precoCalculado));
    }

    @Test
    public void deveCalcularPrecoComAcrescimoParaSalaIMAX() {
        // Cenário: Preço base padrão (40.00) * 1.25 para Sala IMAX = 50.00
        TipoImagem imagemNormal = new TipoImagem();
        imagemNormal.setTipo("2D");

        TipoSala salaImax = new TipoSala();
        salaImax.setTipo("IMAX");

        Sala sala = new Sala();
        sala.setTipoSala(salaImax);

        Sessao sessao = new Sessao();
        sessao.setTipoImagem(imagemNormal);
        sessao.setSala(sala);

        // Execução
        BigDecimal precoCalculado = sessaoService.getValorSessao(sessao);

        // Verificação
        assertEquals(0, new BigDecimal("50.00").compareTo(precoCalculado));
    }
}