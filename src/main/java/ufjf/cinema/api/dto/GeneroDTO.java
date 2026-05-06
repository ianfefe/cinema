package ufjf.cinema.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Genero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneroDTO {

    private Long id;
    private String genero;


    public static GeneroDTO create(Genero genero) {
        ModelMapper modelMapper = new ModelMapper();
        GeneroDTO dto = modelMapper.map(genero, GeneroDTO.class);
        return dto;
    }
}
