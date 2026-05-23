package ufjf.cinema.model.enums;

import lombok.Getter;

@Getter
public enum ClassificacaoIndicativaEnum {

    LIVRE("L", "Livre para todos os públicos"),
    DEZ("10", "Não recomendado para menores de 10 anos"),
    DOZE("12", "Não recomendado para menores de 12 anos"),
    QUATORZE("14", "Não recomendado para menores de 14 anos"),
    DEZESSEIS("16", "Não recomendado para menores de 16 anos"),
    DEZOITO("18", "Não recomendado para menores de 18 anos");

    private final String valor;
    private final String descricao;

    ClassificacaoIndicativaEnum(String valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }
}

