package ufjf.cinema.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ingresso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TipoIngresso tipoIngresso;
    @ManyToOne
    private Compra compra;
    @ManyToOne
    private Assento assento;
    @ManyToOne
    private Sessao sessao;
}
