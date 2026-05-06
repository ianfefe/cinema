package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Sala;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaDTO {
    private Long id;
    private Integer numeroSala;
    private Long idTipoSala;

    public static SalaDTO create(Sala sala) {
        ModelMapper modelMapper = new ModelMapper();
        SalaDTO dto = modelMapper.map(sala, SalaDTO.class);
        return dto;
    }
}
