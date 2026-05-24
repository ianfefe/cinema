package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Endereco;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EnderecoDTO {
    private Long id;

    private String logradouro;
    private String bairro;
    private String cidade;
    private Integer numero;
    public  static EnderecoDTO create(Endereco endereco){
        ModelMapper modelMapper = new ModelMapper();
        EnderecoDTO dto = modelMapper.map(endereco, EnderecoDTO.class);
        return  dto;
    }
}
