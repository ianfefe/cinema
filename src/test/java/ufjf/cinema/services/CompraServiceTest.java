package ufjf.cinema.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.*;
import ufjf.cinema.model.repository.CompraRepository;
import ufjf.cinema.model.repository.IngressoRepository;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private IngressoRepository ingressoRepository;

    // Removeu-se o @InjectMocks daqui
    private CompraService compraService;

    private SessaoService sessaoService;
    private CinemaService cinemaService;
    private ClienteService clienteService;
    private AssentoService assentoService;
    private TipoIngressoService tipoIngressoService;
    private IngressoService IngressoService;


    @BeforeEach
    public void setUp() {
        compraService = new CompraService( compraRepository,
                 ingressoRepository,
                 sessaoService,
                 assentoService,
                 cinemaService,
                 clienteService,
                 tipoIngressoService,
                 IngressoService);

    }

    @Test
    public void deveCalcularValorTotalCorretamente() {
        // Cenário
        Sessao sessao = new Sessao();
        sessao.setPrecoBase(new BigDecimal("40.00"));

        TipoIngresso inteira = new TipoIngresso(); inteira.setTipo("INTEIRA");
        TipoIngresso meia = new TipoIngresso(); meia.setTipo("MEIA-ENTRADA");

        Ingresso i1 = new Ingresso();
        i1.setSessao(sessao);
        i1.setTipoIngresso(inteira);

        Ingresso i2 = new Ingresso();
        i2.setSessao(sessao);
        i2.setTipoIngresso(meia);

        Compra compra = new Compra();
        // AQUI ESTÁ O AJUSTE: Populamos a lista diretamente na entidade
        compra.setIngressos(Arrays.asList(i1, i2));

        // Como mudamos a lógica para ler da lista da entidade,
        // não precisamos mais mockar o ingressoRepository aqui!

        // Execução
        BigDecimal total = compraService.getValorCompra(compra);

        // Verificação
        assertEquals(new BigDecimal("60.00"), total);
    }

    @Test
    public void deveLancarErroSeIngressoForDeCinemaDiferenteDaCompra() {
        // Cenário: Compra registrada para Cinema ID 1, mas ingresso é do Cinema ID 2
        Cinema cinema1 = new Cinema(); cinema1.setId(1L);
        Cinema cinema2 = new Cinema(); cinema2.setId(2L);

        Sala salaDoCinema2 = new Sala(); salaDoCinema2.setCinema(cinema2);
        Sessao sessaoDoCinema2 = new Sessao(); sessaoDoCinema2.setSala(salaDoCinema2);

        Ingresso ingressoInvalido = new Ingresso();
        ingressoInvalido.setSessao(sessaoDoCinema2);

        Compra compra = new Compra();
        compra.setCinema(cinema1);
        compra.setIngressos(Arrays.asList(ingressoInvalido));

        // Execução e Verificação
        assertThrows(RegraNegocioException.class, () -> {
            compraService.validar(compra);
        });
    }
}