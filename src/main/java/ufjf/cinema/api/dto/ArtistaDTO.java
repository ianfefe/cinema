package ufjf.cinema.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Artista;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistaDTO {
    private Long id;
    private String nome;

    public static ArtistaDTO create(Artista artista){
        ModelMapper modelMapper = new ModelMapper();
        ArtistaDTO dto = modelMapper.map(artista, ArtistaDTO.class);
        return dto;
    }

}
