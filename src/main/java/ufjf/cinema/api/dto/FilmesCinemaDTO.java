package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.FilmesCinema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmesCinemaDTO {
    private Long id;
    private Long cinemaId;
    private Long filmeId;

    public static FilmesCinemaDTO create(FilmesCinema filmesCinema){
        ModelMapper modelMapper = new ModelMapper();
        FilmesCinemaDTO dto = modelMapper.map(filmesCinema, FilmesCinemaDTO.class);
        return dto;
    }

}
