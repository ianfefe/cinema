package ufjf.cinema.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.repository.FilmeRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FilmeServiceTest {

    @Mock
    private FilmeRepository filmeRepository;

    @InjectMocks
    private FilmeService filmeService;

    @Test
    public void deveLancarErroParaFilmeComDuracaoNegativa() {
        Filme filmeInvalido = new Filme();
        filmeInvalido.setNome("Inception");
        filmeInvalido.setSinopse("Um thriller dentro da mente.");
        filmeInvalido.setPoster("url_poster.jpg");
        filmeInvalido.setDuracao(-15); // Duração inválida
        filmeInvalido.setClassificacaoIndicativa("LIVRE");

        assertThrows(RegraNegocioException.class, () -> {
            filmeService.validar(filmeInvalido);
        });
    }

    @Test
    public void devePassarNaValidacaoSeFilmeEstiverCorreto() {
        Filme filmeValido = new Filme();
        filmeValido.setNome("Interstellar");
        filmeValido.setSinopse("Viagem pelo espaço-tempo.");
        filmeValido.setPoster("url_poster.jpg");
        filmeValido.setDuracao(169);
        filmeValido.setClassificacaoIndicativa("LIVRE");

        assertDoesNotThrow(() -> {
            filmeService.validar(filmeValido);
        });
    }
}