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
    private Integer matricula;
    private String nivelPermissao;
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Long endereco;
    public static FuncionarioDTO create(Funcionario funcionario){
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioDTO dto = modelMapper.map(funcionario, FuncionarioDTO.class);
        return dto;
    }

}
