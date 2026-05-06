package api.dto;


import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Endereco;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CinemaDto {
    private Long id;
    private String nome;
    private Long enderecoId;

    public static CinemaDto create(Cinema cinema){
        ModelMapper modelMapper = new ModelMapper();
        CinemaDto dto = modelMapper.map(cinema, CinemaDto.class);
        return  dto;
    }

}
