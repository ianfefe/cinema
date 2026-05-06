package api.dto;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ufjf.cinema.model.entity.Cinema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmeDto {
    private Long id;

    private String nome;
    private String poster;
    private String sinopse;
    private Integer duracao;
    private Integer classificaoIndicativa;

    private Long cinema;
}
