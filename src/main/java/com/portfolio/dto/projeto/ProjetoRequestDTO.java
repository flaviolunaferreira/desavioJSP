package com.portfolio.dto.projeto;

import com.portfolio.model.ProjetoEntity;
import com.portfolio.model.PessoaEntity;
import com.portfolio.model.enumeration.RiscoProjeto;
import com.portfolio.model.enumeration.StatusProjeto;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProjetoRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;

    @NotNull(message = "Data prevista de fim é obrigatória")
    private LocalDate dataPrevisaoFim;

    private LocalDate dataFim;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @NotNull(message = "Status é obrigatório")
    private String status;

    @DecimalMin(value = "0.0", message = "Orçamento não pode ser negativo")
    @Digits(integer = 13, fraction = 2, message = "Orçamento deve ter no máximo 13 dígitos e 2 decimais")
    private BigDecimal orcamento;

    private String risco;

    @NotNull(message = "ID do gerente é obrigatório")
    private Long idGerente;

    public ProjetoEntity toEntity() {
        ProjetoEntity projeto = new ProjetoEntity();
        projeto.setNome(this.nome);
        projeto.setDataInicio(this.dataInicio);
        projeto.setDataPrevisaoFim(this.dataPrevisaoFim);
        projeto.setDataFim(this.dataFim);
        projeto.setDescricao(this.descricao);
        projeto.setStatus(StatusProjeto.valueOf(this.status));
        projeto.setOrcamento(this.orcamento);
        projeto.setRisco(RiscoProjeto.valueOf(this.risco));

        PessoaEntity gerente = new PessoaEntity();
        gerente.setId(this.idGerente);
        projeto.setGerente(gerente);

        return projeto;
    }
}