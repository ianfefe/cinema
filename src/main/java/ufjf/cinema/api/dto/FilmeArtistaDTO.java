package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.FilmesArtista;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmeArtistaDTO {
    private Long id;
    private Long filmeId;
    private Long artistaId;
    public static FilmeArtistaDTO create(FilmesArtista filmesArtista){
        ModelMapper modelMapper = new ModelMapper();
        FilmeArtistaDTO dto = modelMapper.map(filmesArtista, FilmeArtistaDTO.class);
        return  dto;
    }
}
