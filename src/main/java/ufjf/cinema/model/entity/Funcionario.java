package ufjf.cinema.model.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Funcionario extends Usuario {

    private Integer matricula;
    private String nivelPermissao;
}
