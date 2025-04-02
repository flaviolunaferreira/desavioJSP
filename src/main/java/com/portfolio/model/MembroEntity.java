package com.portfolio.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "membro")
@MappedSuperclass
public class MembroEntity extends BasicEntity{

    @Id
    @ManyToOne
    @JoinColumn(name = "id_projeto")
    private ProjetoEntity projeto;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private PessoaEntity pessoa;
}