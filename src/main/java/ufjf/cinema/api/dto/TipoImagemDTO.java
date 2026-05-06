package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Tipo;
import ufjf.cinema.model.entity.TipoAssento;
import ufjf.cinema.model.entity.TipoAudio;
import ufjf.cinema.model.entity.TipoImagem;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoImagemDTO {

    private Long id;
    private String tipo;

    public static TipoImagemDTO create(TipoImagem tipoImagem) {
        ModelMapper modelMapper = new ModelMapper();
        TipoImagemDTO dto = modelMapper.map(tipoImagem, TipoImagemDTO.class);
        return dto;
    }
}
