package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoSalaDTO {

    private Long id;
    private String tipo;

    public static TipoSalaDTO create(TipoSala tipoSala) {
        ModelMapper modelMapper = new ModelMapper();
        TipoSalaDTO dto = modelMapper.map(tipoSala, TipoSalaDTO.class);
        return dto;
    }
}
