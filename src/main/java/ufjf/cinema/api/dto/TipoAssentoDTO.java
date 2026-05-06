package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Tipo;
import ufjf.cinema.model.entity.TipoAssento;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoAssentoDTO {

    private Long id;
    private String tipo;

    public static TipoAssentoDTO create(TipoAssento tipoAssento) {
        ModelMapper modelMapper = new ModelMapper();
        TipoAssentoDTO dto = modelMapper.map(tipoAssento, TipoAssentoDTO.class);
        return dto;
    }
}
