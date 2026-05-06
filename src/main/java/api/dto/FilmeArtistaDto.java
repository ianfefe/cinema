package api.dto;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ufjf.cinema.model.entity.Artista;
import ufjf.cinema.model.entity.Filme;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmeArtistaDto {
    private Long id;

    private Long filmeId;
    private Long artistaId;
}
