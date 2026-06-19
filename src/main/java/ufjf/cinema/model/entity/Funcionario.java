package ufjf.cinema.model.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Funcionario extends Pessoa {

    private String matricula;

    @ManyToOne
    private Cinema cinema;
}
