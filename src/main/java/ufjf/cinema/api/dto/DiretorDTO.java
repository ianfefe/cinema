package api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Diretor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DiretorDTO {
    private Long id;

    private String nome;
    public static DiretorDTO create(Diretor diretor){
        ModelMapper modelMapper = new ModelMapper();
        DiretorDTO dto = modelMapper.map(diretor, DiretorDTO.class);
        return  dto;

    }
}
