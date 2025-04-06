package com.portfolio.model;

import com.portfolio.model.enumeration.StatusTarefa;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tarefa")
public class TarefaEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_projeto", nullable = false,
            foreignKey = @ForeignKey(name = "fk_tarefa_projeto"))
    private ProjetoEntity projeto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_responsavel",
            foreignKey = @ForeignKey(name = "fk_tarefa_responsavel"))
    private PessoaEntity responsavel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTarefa status = StatusTarefa.PENDENTE;

    @Column(name = "data_limite")
    private LocalDateTime dataLimite;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;
}

