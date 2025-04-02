package com.portfolio.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tarefa")
public class TarefaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_projeto", nullable = false)
    private ProjetoEntity projeto;

    @ManyToOne
    @JoinColumn(name = "id_responsavel")
    private PessoaEntity responsavel;

    @Column(nullable = false)
    private String status;
}