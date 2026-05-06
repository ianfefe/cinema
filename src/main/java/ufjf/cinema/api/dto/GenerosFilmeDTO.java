package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.GenerosFilme;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerosFilmeDTO {
    private Long id;
    private Long idFilme;
    private Long idGenero;

    public static GenerosFilmeDTO create(GenerosFilme generosFilme){
        ModelMapper modelMapper = new ModelMapper();
        GenerosFilmeDTO dto = modelMapper.map(generosFilme, GenerosFilmeDTO.class);
        return dto;
    }
}
