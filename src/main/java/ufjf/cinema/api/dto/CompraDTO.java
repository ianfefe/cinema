package api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Compra;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {

    private Long id;

    private BigDecimal total;
    private String dataHora;
    private String formaPagamento;

    private Long usuarioId;
    private Long cinemaId;

    private Long clienteId;
    public static CompraDTO create(Compra compra){
        ModelMapper modelMapper = new ModelMapper();
        CompraDTO dto = modelMapper.map(compra, CompraDTO.class);
        return  dto;
    }
}
