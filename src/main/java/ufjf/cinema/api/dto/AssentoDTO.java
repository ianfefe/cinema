package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Assento;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssentoDTO {
    private Long id;
    private String posicao;
    private Long tipoAssentoId;
    private Long idSala;

    public static AssentoDTO create(Assento assento){
        ModelMapper modelMapper = new ModelMapper();
        AssentoDTO dto = modelMapper.map(assento, AssentoDTO.class);
        return  dto;
    }
}
