package ufjf.cinema.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Funcionario extends Usuario {

    private Integer matricula;
    private String nivelPermissao;
}
