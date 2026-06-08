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
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setSenha(cliente.getSenha());
        dto.setTelefone(cliente.getTelefone());
        if (cliente.getEndereco() != null) {
            dto.setEndereco(cliente.getEndereco().getId());
        }
        return dto;
    }
}
