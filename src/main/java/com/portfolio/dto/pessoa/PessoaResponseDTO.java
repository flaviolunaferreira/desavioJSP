package com.portfolio.dto.pessoa;

import com.portfolio.model.PessoaEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PessoaResponseDTO {
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private Boolean funcionario;
    private Boolean gerente;
    private String cargo;

    public static PessoaResponseDTO fromEntity(PessoaEntity pessoa) {
        PessoaResponseDTO dto = new PessoaResponseDTO();
        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome());
        dto.setDataNascimento(pessoa.getDataNascimento());
        dto.setCpf(pessoa.getCpf());
        dto.setFuncionario(pessoa.isFuncionario());
        dto.setGerente(pessoa.isGerente());
        dto.setCargo(determinarCargo(pessoa));
        return dto;
    }

    private static String determinarCargo(PessoaEntity pessoa) {
        if (pessoa.isGerente()) return "Gerente";
        if (pessoa.isFuncionario()) return "Funcionário";
        return "Colaborador Externo";
    }
}