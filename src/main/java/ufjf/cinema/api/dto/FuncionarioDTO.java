package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ufjf.cinema.model.entity.Funcionario;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FuncionarioDTO {
    private Long id;
    private Long cinemaId;

    private String nome;
    private String email;
    private String telefone;
    private Long endereco;
    private String matricula;

    public static FuncionarioDTO create(Funcionario funcionario) {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setEmail(funcionario.getEmail());
        dto.setTelefone(funcionario.getTelefone());
        dto.setMatricula(funcionario.getMatricula());
        if (funcionario.getEndereco() != null) {
            dto.setEndereco(funcionario.getEndereco().getId());
        }
        if (funcionario.getCinema() != null) {
            dto.setCinemaId(funcionario.getCinema().getId());
        }
        return dto;
    }

}
