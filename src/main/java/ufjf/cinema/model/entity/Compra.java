package ufjf.cinema.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal total;
    private String dataHora;
    private String formaPagamento;

    @ManyToOne
    private Funcionario funcionario;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Cinema cinema;

    @JsonIgnore
    @OneToMany(mappedBy = "compra")
    private List<Ingresso> ingressos;
}
