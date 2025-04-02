package com.portfolio.dto.projeto;

import com.portfolio.model.PessoaEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class ProjetoResponseDTO {

    private Long id;

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
