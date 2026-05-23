package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.FuncionariosCinema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioCinemaDTO {
    private Long id;
    private Long cinemaId;
    private Long funcionarioId;

    public static FuncionarioCinemaDTO create(FuncionariosCinema funcionariosCinema){
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioCinemaDTO dto = modelMapper.map(funcionariosCinema, FuncionarioCinemaDTO.class);
        return dto;
    }

}
