package api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Funcionario;
import ufjf.cinema.model.entity.FuncionariosCinema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioCInemaDTO {
    private Long id;
    private Long cinemaId;
    private Long funcionarioId;

    public static FuncionarioCInemaDTO create(FuncionariosCinema funcionariosCinema){
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioCInemaDTO dto = modelMapper.map(funcionariosCinema, FuncionarioCInemaDTO.class);
        return dto;
    }

}
