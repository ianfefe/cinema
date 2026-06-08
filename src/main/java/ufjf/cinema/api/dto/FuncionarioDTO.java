package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Funcionario;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FuncionarioDTO {
    private String matricula;
    private String nivelPermissao;
    private Long id;
    private Long cinemaId;

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Long endereco;
    public static FuncionarioDTO create(Funcionario funcionario){
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setEmail(funcionario.getEmail());
        dto.setSenha(funcionario.getSenha());
        dto.setTelefone(funcionario.getTelefone());
        dto.setMatricula(funcionario.getMatricula());
        dto.setNivelPermissao(funcionario.getNivelPermissao());
        if (funcionario.getEndereco() != null) {
            dto.setEndereco(funcionario.getEndereco().getId());
        }
        if (funcionario.getCinema() != null) {
            dto.setCinemaId(funcionario.getCinema().getId());
        }
        return dto;
    }

}
