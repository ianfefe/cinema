package ufjf.cinema.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal precoBase;
    private String horario;

    @ManyToOne
    private Cinema cinema;
    @ManyToOne
    private TipoImagem tipoImagem;
    @ManyToOne
    private TipoAudio tipoAudio;
    @ManyToOne
    private Filme filme;
}
