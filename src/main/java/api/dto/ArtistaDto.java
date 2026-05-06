package api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Artista;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistaDto {
    private Long id;
    private String nome;

    public static ArtistaDto create(Artista artista){
        ModelMapper modelMapper = new ModelMapper();
        ArtistaDto dto = modelMapper.map(artista, ArtistaDto.class);
        return dto;
    }

}
