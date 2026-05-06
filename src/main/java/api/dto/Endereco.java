package api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Endereco {
    private Long id;

    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;

}
