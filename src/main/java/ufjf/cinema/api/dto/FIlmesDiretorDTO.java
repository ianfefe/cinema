package api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.FilmesDiretor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FIlmesDiretorDTO {
    private Long id;
    private Long filmeId;
    private Long diretorId;

    public static FIlmesDiretorDTO create(FilmesDiretor filmesDiretor){
        ModelMapper modelMapper = new ModelMapper();
        FIlmesDiretorDTO dto = modelMapper.map(filmesDiretor, FIlmesDiretorDTO.class);
        return dto;
    }

}
