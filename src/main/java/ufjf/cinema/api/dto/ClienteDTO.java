package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Cliente;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Long endereco;

    public static ClienteDTO create(Cliente cliente){
        ModelMapper modelMapper = new ModelMapper();
        ClienteDTO dto = modelMapper.map(cliente, ClienteDTO.class);
        return dto;
    }
}
