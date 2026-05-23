package ufjf.cinema.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.crypto.SealedObject;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SessoesCinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cinema cinema;
    @ManyToOne
    private Sessao sessao;

}
