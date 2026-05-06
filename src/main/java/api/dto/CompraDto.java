package api.dto;


import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Cliente;
import ufjf.cinema.model.entity.Usuario;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDto {

    private Long id;

    private BigDecimal total;
    private String dataHora;
    private String formaPagamento;

    private Long usuarioId;
    private Long cinemaId;

    private Long clienteId;
}
