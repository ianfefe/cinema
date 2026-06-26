package ufjf.cinema.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ufjf.cinema.model.entity.Compra;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {

    private Long id;

    private BigDecimal total;
    private String dataHora;
    private String formaPagamento;
    private String status;

    private Long funcionarioId;
    private Long cinemaId;
    private Long clienteId;

    private List<IngressoDTO> ingressos;


    public static CompraDTO create(Compra compra){
        ModelMapper modelMapper = new ModelMapper();
        CompraDTO dto = modelMapper.map(compra, CompraDTO.class);

        if (compra.getIngressos() != null) {
            dto.setIngressos(compra.getIngressos().stream()
                    .map(IngressoDTO::create)
                    .collect(Collectors.toList()));
        }

        return  dto;
    }
}
