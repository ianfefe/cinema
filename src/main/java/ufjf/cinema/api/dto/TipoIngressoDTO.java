package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoIngressoDTO {

    private Long id;
    private String tipo;

    public static TipoIngressoDTO create(TipoIngresso tipoIngresso) {
        ModelMapper modelMapper = new ModelMapper();
        TipoIngressoDTO dto = modelMapper.map(tipoIngresso, TipoIngressoDTO.class);
        return dto;
    }
}
