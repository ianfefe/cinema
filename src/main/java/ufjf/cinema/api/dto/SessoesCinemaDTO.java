package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.FuncionariosCinema;
import ufjf.cinema.model.entity.SessoesCinema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessoesCinemaDTO {
    private Long id;
    private Long cinemaId;
    private Long sessaoId;

    public static SessoesCinemaDTO create(SessoesCinema sessoesCinema){
        ModelMapper modelMapper = new ModelMapper();
        SessoesCinemaDTO dto = modelMapper.map(sessoesCinema, SessoesCinemaDTO.class);
        return dto;
    }

}
