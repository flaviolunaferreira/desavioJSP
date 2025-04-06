package com.portfolio.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "membro")
@IdClass(MembroId.class)
public class MembroEntity extends BasicEntity {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_projeto", nullable = false,
            foreignKey = @ForeignKey(name = "fk_membro_projeto"))
    private ProjetoEntity projeto;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pessoa", nullable = false,
            foreignKey = @ForeignKey(name = "fk_membro_pessoa"))
    private PessoaEntity pessoa;

    @Column(name = "data_entrada", nullable = false)
    private LocalDate dataEntrada;

    @Column(name = "funcao", length = 50)
    private String funcao;
}
