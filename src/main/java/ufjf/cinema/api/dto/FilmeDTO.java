package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Filme;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmeDTO {
    private Long id;

    private String nome;
    private String poster;
    private String sinopse;
    private Integer duracao;
    private Integer classificaoIndicativa;
    private Long cinema;
    public  static FilmeDTO create(Filme filme){
        ModelMapper modelMapper = new ModelMapper();
        FilmeDTO dto = modelMapper.map(filme, FilmeDTO.class);
        return dto;
    }
}
