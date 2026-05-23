package ufjf.cinema.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ufjf.cinema.model.enums.ClassificacaoIndicativaEnum;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String poster;
    private String sinopse;
    private Integer duracao;

    @Enumerated(EnumType.STRING)
    private ClassificacaoIndicativaEnum classificaoIndicativa;

    @ManyToOne
    private Cinema cinema;
}
