package ufjf.cinema.model.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Funcionario extends Usuario {

    private String matricula;
    private String nivelPermissao;

    @ManyToOne
    private Cinema cinema;
}
