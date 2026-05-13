package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.TipoAudio;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoAudioDTO {

    private Long id;
    private String tipo;

    public static TipoAudioDTO create(TipoAudio tipoAudio) {
        ModelMapper modelMapper = new ModelMapper();
        TipoAudioDTO dto = modelMapper.map(tipoAudio, TipoAudioDTO.class);
        return dto;
    }
}
