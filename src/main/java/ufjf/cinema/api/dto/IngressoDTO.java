package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Ingresso;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngressoDTO {

    private Long id;
    private Long idCinema;
    private Long idTipoIngresso;
    private Long idCompra;
    private Long idAssento;
    private Long idSessao;

    public static IngressoDTO create(Ingresso ingresso) {
        ModelMapper modelMapper = new ModelMapper();
        IngressoDTO dto = modelMapper.map(ingresso, IngressoDTO.class);
        return dto;
    }
}
