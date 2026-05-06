package api.dto;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ufjf.cinema.model.entity.Endereco;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Long endereco;

    public static 
}
