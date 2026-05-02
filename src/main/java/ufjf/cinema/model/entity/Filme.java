package ufjf.cinema.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer classificaoIndicativa;

    @ManyToOne
    private Cinema cinema;
}
