package com.portfolio.dto.projeto;

import com.portfolio.model.PessoaEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjetoRequestDTO {

    private String nome;

    private LocalDate dataInicio;

    private LocalDate dataPrevisaoFim;

    private LocalDate dataFim;

    private String descricao;

    private String status;

    private Double orcamento;

    private String risco;

    private PessoaEntity gerente;

}
