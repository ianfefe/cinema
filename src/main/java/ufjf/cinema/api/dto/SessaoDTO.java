package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Sala;
import ufjf.cinema.model.entity.Sessao;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessaoDTO {
    private Long id;
    private BigDecimal preco;
    private String horario;
    private Long idCinema;
    private Long idTipoImagem;
    private Long idTipoAudio;
    private Long idFilme;

    public static SessaoDTO create(Sessao sessao) {
        ModelMapper modelMapper = new ModelMapper();
        SessaoDTO dto = modelMapper.map(sessao, SessaoDTO.class);
        return dto;
    }
}
