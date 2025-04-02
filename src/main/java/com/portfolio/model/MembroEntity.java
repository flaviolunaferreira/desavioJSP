package com.portfolio.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "membro")
public class MembroEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_projeto")
    private ProjetoEntity projeto;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private PessoaEntity pessoa;
}