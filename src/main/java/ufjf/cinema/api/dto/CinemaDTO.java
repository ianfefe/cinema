package ufjf.cinema.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Cinema;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CinemaDTO {
    private Long id;
    private String nome;
    private Long enderecoId;

    public static CinemaDTO create(Cinema cinema){
        ModelMapper modelMapper = new ModelMapper();
        CinemaDTO dto = modelMapper.map(cinema, CinemaDTO.class);
        return  dto;
    }

}
