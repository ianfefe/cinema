package api.dto;


import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Assento;
import ufjf.cinema.model.entity.TipoAssento;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssentoDto {
    private Long id;
    private String posicao;
    private Long tipoAssentoId;

    public static AssentoDto create(Assento assento){
        ModelMapper modelMapper = new ModelMapper();
        AssentoDto dto = modelMapper.map(assento, AssentoDto.class);
        return  dto;
    }
}
