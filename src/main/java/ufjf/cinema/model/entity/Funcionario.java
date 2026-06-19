package ufjf.cinema.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Funcionario extends Pessoa {

    private String matricula;

    @ManyToOne
    private Cinema cinema;
}
