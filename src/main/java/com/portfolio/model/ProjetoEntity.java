package com.portfolio.model;

import com.portfolio.model.enumeration.RiscoProjeto;
import com.portfolio.model.enumeration.StatusProjeto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "projeto")
public class ProjetoEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_previsao_fim", nullable = false)
    private LocalDate dataPrevisaoFim;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusProjeto status;

    @Column(columnDefinition = "DECIMAL(15,2)")
    private BigDecimal orcamento;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private RiscoProjeto risco;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_gerente", nullable = false,
            foreignKey = @ForeignKey(name = "fk_projeto_gerente"))
    private PessoaEntity gerente;
}

