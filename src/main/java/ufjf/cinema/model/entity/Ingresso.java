package ufjf.cinema.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ingresso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cinema cinema;
    @ManyToOne
    private TipoIngresso tipoIngresso;
    @ManyToOne
    private Compra compra;
    @ManyToOne
    private Assento assento;
    @ManyToOne
    private Sessao sessao;
}
