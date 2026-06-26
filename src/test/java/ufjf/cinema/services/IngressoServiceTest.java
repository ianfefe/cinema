package ufjf.cinema.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufjf.cinema.model.entity.Assento;
import ufjf.cinema.model.entity.Ingresso;
import ufjf.cinema.model.entity.Sessao;
import ufjf.cinema.model.repository.IngressoRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IngressoServiceTest {

    @Mock
    private IngressoRepository ingressoRepository;

    @InjectMocks
    private IngressoService ingressoService;

    @Test
    public void deveRetornarTrueSeLugarDisponivel() {
        // Cenário
        Ingresso ingresso = new Ingresso();
        ingresso.setSessao(new Sessao());
        ingresso.setAssento(new Assento());

        // Simulação
        when(ingressoRepository.existsIngressoBySessaoAndAssento(ingresso.getSessao(), ingresso.getAssento()))
                .thenReturn(false);

        // Execução
        boolean disponivel = ingressoService.lugarDisponivel(ingresso);

        // Verificação
        assertTrue(disponivel);
    }
}