package com.portfolio.dto.tarefa;

import com.portfolio.model.TarefaEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TarefaResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String status;
    private LocalDateTime dataLimite;
    private LocalDateTime dataConclusao;
    private ProjetoInfoDTO projeto;
    private PessoaInfoDTO responsavel;

    @Data
    public static class ProjetoInfoDTO {
        private Long id;
        private String nome;
    }

    @Data
    public static class PessoaInfoDTO {
        private Long id;
        private String nome;
        private String cargo;
    }

    public static TarefaResponseDTO fromEntity(TarefaEntity tarefa) {
        TarefaResponseDTO dto = new TarefaResponseDTO();
        dto.setId(tarefa.getId());
        dto.setTitulo(tarefa.getTitulo());
        dto.setDescricao(tarefa.getDescricao());
        dto.setStatus(String.valueOf(tarefa.getStatus()));
        dto.setDataLimite(tarefa.getDataLimite());
        dto.setDataConclusao(tarefa.getDataConclusao());

        if (tarefa.getProjeto() != null) {
            ProjetoInfoDTO projetoInfo = new ProjetoInfoDTO();
            projetoInfo.setId(tarefa.getProjeto().getId());
            projetoInfo.setNome(tarefa.getProjeto().getNome());
            dto.setProjeto(projetoInfo);
        }

        if (tarefa.getResponsavel() != null) {
            PessoaInfoDTO pessoaInfo = new PessoaInfoDTO();
            pessoaInfo.setId(tarefa.getResponsavel().getId());
            pessoaInfo.setNome(tarefa.getResponsavel().getNome());
            pessoaInfo.setCargo(tarefa.getResponsavel().isGerente() ? "Gerente" :
                    tarefa.getResponsavel().isFuncionario() ? "Funcionário" : "Colaborador");
            dto.setResponsavel(pessoaInfo);
        }

        return dto;
    }
}