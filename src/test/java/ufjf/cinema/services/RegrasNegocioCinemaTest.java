package ufjf.cinema.services;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.*;
import ufjf.cinema.model.repository.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegrasNegocioCinemaTest {

    // ==========================================
    // 1. TESTES DE USUÁRIO (Segurança e Duplicação)
    // ==========================================
    @Nested
    class UsuarioServiceTest {

        @Mock
        private UsuarioRepository usuarioRepository;

        @InjectMocks
        private UsuarioService usuarioService;

        @Test
        public void deveLancarErroSeLoginForVazioOuNulo() {
            Usuario usuario = new Usuario();
            usuario.setLogin("");
            usuario.setSenha("123456");

            assertThrows(RegraNegocioException.class, () -> usuarioService.validar(usuario));
        }

        @Test
        public void deveLancarErroSeLoginJaEstiverEmUso() {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setLogin("admin");
            novoUsuario.setSenha("1234");

            // Simula que o banco já achou alguém com esse login
            when(usuarioRepository.findByLogin("admin")).thenReturn(Optional.of(new Usuario()));

            assertThrows(RegraNegocioException.class, () -> usuarioService.validar(novoUsuario));
        }
    }

    // ==========================================
    // 2. TESTES DE INGRESSO E ASSENTOS
    // ==========================================
    @Nested
    class IngressoServiceTest {

        @Mock
        private IngressoRepository ingressoRepository;

        @InjectMocks
        private IngressoService ingressoService;

        @Test
        public void deveLancarErroAoTentarComprarAssentoDeOutraSala() {
            Sala sala1 = new Sala(); sala1.setId(1L);
            Sala sala2 = new Sala(); sala2.setId(2L);

            Sessao sessao = new Sessao(); sessao.setId(1L); sessao.setSala(sala1);
            Assento assento = new Assento(); assento.setId(1L); assento.setSala(sala2); // Sala diferente!

            Ingresso ingresso = new Ingresso();
            ingresso.setSessao(sessao);
            ingresso.setAssento(assento);

            assertThrows(RegraNegocioException.class, () -> ingressoService.validar(ingresso));
        }

        @Test
        public void deveLancarErroSeAssentoJaEstiverVendido() {
            Sala sala = new Sala(); sala.setId(1L);
            Sessao sessao = new Sessao(); sessao.setId(1L); sessao.setSala(sala);
            Assento assento = new Assento(); assento.setId(1L); assento.setSala(sala);

            Ingresso ingresso = new Ingresso();
            ingresso.setSessao(sessao);
            ingresso.setAssento(assento);

            // Simula que o banco de dados avisa que o assento já tem dono
            when(ingressoRepository.existsIngressoBySessaoAndAssento(any(), any())).thenReturn(true);

            assertThrows(RegraNegocioException.class, () -> ingressoService.validar(ingresso));
        }
    }

    // ==========================================
    // 3. TESTES DE RELACIONAMENTOS N:N (Evitar Duplicatas)
    // ==========================================
    // ==========================================
    // 3. TESTES DE RELACIONAMENTOS N:N (Evitar Duplicatas)
    // ==========================================
    @Nested
    class RelacionamentosTest {

        @Mock private FilmesDiretorRepository filmesDiretorRepository;
        @Mock private GenerosFilmeRepository generosFilmeRepository;
        @Mock private SessoesCinemaRepository sessoesCinemaRepository;

        // Sem @InjectMocks! Vamos instanciar na mão para não confundir o Mockito
        private FilmesDiretorService filmesDiretorService;
        private GenerosFilmeService generosFilmeService;
        private SessoesCinemaService sessoesCinemaService;

        @org.junit.jupiter.api.BeforeEach
        public void setUp() {
            // Injeção cirúrgica e manual passando os repositórios corretos
            filmesDiretorService = new FilmesDiretorService(filmesDiretorRepository);
            generosFilmeService = new GenerosFilmeService(generosFilmeRepository);
            sessoesCinemaService = new SessoesCinemaService(sessoesCinemaRepository);
        }

        @Test
        public void deveLancarErroAoAdicionarMesmoDiretorAoFilme() {
            Filme filme = new Filme(); filme.setId(1L);
            Diretor diretor = new Diretor(); diretor.setId(1L);

            FilmesDiretor vinculo = new FilmesDiretor();
            vinculo.setFilme(filme);
            vinculo.setDiretor(diretor);

            when(filmesDiretorRepository.existsByFilmeAndDiretor(filme, diretor)).thenReturn(true);

            assertThrows(RegraNegocioException.class, () -> filmesDiretorService.validar(vinculo));
        }

        @Test
        public void deveLancarErroAoAdicionarMesmoGeneroAoFilme() {
            Filme filme = new Filme(); filme.setId(1L);
            Genero genero = new Genero(); genero.setId(1L);

            GenerosFilme vinculo = new GenerosFilme();
            vinculo.setFilme(filme);
            vinculo.setGenero(genero);

            // Descomente quando criar o existsByFilmeAndGenero no repository
            // when(generosFilmeRepository.existsByFilmeAndGenero(filme, genero)).thenReturn(true);
            // assertThrows(RegraNegocioException.class, () -> generosFilmeService.validar(vinculo));
        }

        @Test
        public void deveLancarErroAoVincularSessaoJaExistenteNoCinema() {
            Cinema cinema = new Cinema(); cinema.setId(1L);
            Sessao sessao = new Sessao(); sessao.setId(1L);

            SessoesCinema vinculo = new SessoesCinema();
            vinculo.setCinema(cinema);
            vinculo.setSessao(sessao);

            // Descomente quando criar o existsByCinemaAndSessao no repository
            // when(sessoesCinemaRepository.existsByCinemaAndSessao(cinema, sessao)).thenReturn(true);
            // assertThrows(RegraNegocioException.class, () -> sessoesCinemaService.validar(vinculo));
        }
    }

}