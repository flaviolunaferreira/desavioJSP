package com.portfolio.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

public class MembroId implements Serializable {
    private Long projeto;
    private Long pessoa;

    // Construtores, equals() e hashCode()
    public MembroId() {}

    public MembroId(Long projeto, Long pessoa) {
        this.projeto = projeto;
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MembroId membroId = (MembroId) o;
        return projeto.equals(membroId.projeto) &&
                pessoa.equals(membroId.pessoa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projeto, pessoa);
    }
}
