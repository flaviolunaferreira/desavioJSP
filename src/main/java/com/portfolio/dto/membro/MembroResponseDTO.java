package com.portfolio.dto.membro;

import com.portfolio.model.MembroEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MembroResponseDTO {
    private ProjetoInfoDTO projeto;
    private PessoaInfoDTO pessoa;
    private LocalDate dataEntrada;
    private String funcao;

    @Data
    public static class ProjetoInfoDTO {
        private Long id;
        private String nome;
        private String status;
    }

    @Data
    public static class PessoaInfoDTO {
        private Long id;
        private String nome;
        private String cargo;
    }

    public static MembroResponseDTO fromEntity(MembroEntity membro) {
        MembroResponseDTO dto = new MembroResponseDTO();

        // Projeto
        ProjetoInfoDTO projetoInfo = new ProjetoInfoDTO();
        projetoInfo.setId(membro.getProjeto().getId());
        projetoInfo.setNome(membro.getProjeto().getNome());
        projetoInfo.setStatus(String.valueOf(membro.getProjeto().getStatus()));
        dto.setProjeto(projetoInfo);

        // Pessoa
        PessoaInfoDTO pessoaInfo = new PessoaInfoDTO();
        pessoaInfo.setId(membro.getPessoa().getId());
        pessoaInfo.setNome(membro.getPessoa().getNome());
        pessoaInfo.setCargo(membro.getPessoa().isGerente() ? "Gerente" :
                membro.getPessoa().isFuncionario() ? "Funcionário" : "Colaborador");
        dto.setPessoa(pessoaInfo);

        dto.setDataEntrada(membro.getDataEntrada());
        dto.setFuncao(membro.getFuncao());

        return dto;
    }
}