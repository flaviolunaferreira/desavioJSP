package com.portfolio.dto.pessoa;

import com.portfolio.model.PessoaEntity;
import lombok.Data;

@Data
public class PessoaProjetoDTO {
    private Long id;
    private String nome;
    private String cargo;

    public static PessoaProjetoDTO fromEntity(PessoaEntity pessoa) {
        PessoaProjetoDTO dto = new PessoaProjetoDTO();
        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome());
        dto.setCargo(pessoa.isGerente() ? "Gerente" : "Funcionário");
        return dto;
    }
}