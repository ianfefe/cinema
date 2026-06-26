package ufjf.cinema.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.entity.FilmesCinema;
import ufjf.cinema.model.repository.FilmesCinemaRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FilmesCinemaServiceTest {

    @Mock
    private FilmesCinemaRepository filmesCinemaRepository;

    @InjectMocks
    private FilmesCinemaService filmesCinemaService;

    @Test
    public void deveLancarErroAoVincularFilmeSemCinema() {
        // Cenário: Tentando disponibilizar um filme no catálogo, mas sem informar o cinema
        FilmesCinema vinculoInvalido = new FilmesCinema();
        vinculoInvalido.setFilme(new Filme());
        vinculoInvalido.setCinema(null); // Cinema nulo

        // Execução e Verificação
        assertThrows(RegraNegocioException.class, () -> {
            filmesCinemaService.validar(vinculoInvalido);
        });
    }

    @Test
    public void devePassarNaValidacaoSeVinculoCinemaFilmeForValido() {
        // Cenário: Vínculo perfeito com instâncias configuradas
        Cinema cinema = new Cinema();
        cinema.setId(1L);

        Filme filme = new Filme();
        filme.setId(1L);

        FilmesCinema vinculoValido = new FilmesCinema();
        vinculoValido.setCinema(cinema);
        vinculoValido.setFilme(filme);

        // Execução e Verificação
        assertDoesNotThrow(() -> {
            filmesCinemaService.validar(vinculoValido);
        });
    }
}