package api.dto;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Funcionario;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioCInema {
    private Long id;
    private Long cinemaId;
    private Long funcionarioId;

}
