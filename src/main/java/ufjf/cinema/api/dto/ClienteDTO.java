package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ufjf.cinema.model.entity.Cliente;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Long enderecoId;

    private UsuarioDTO usuario;

    public static ClienteDTO create(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        if (cliente.getEndereco() != null) {
            dto.setEnderecoId(cliente.getEndereco().getId());
        }
        if (cliente.getUsuario() != null) {
            dto.setUsuario(UsuarioDTO.create(cliente.getUsuario()));
        }
        return dto;
    }
}
