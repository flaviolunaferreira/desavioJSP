package com.portfolio.dto.tarefa;

import com.portfolio.model.PessoaEntity;
import com.portfolio.model.enumeration.StatusTarefa;
import com.portfolio.model.TarefaEntity;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TarefaUpdateDTO {

    @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
    private String titulo;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    private String status;
    private LocalDateTime dataLimite;
    private Long idResponsavel;

    public void applyToEntity(TarefaEntity tarefa) {
        if (this.titulo != null) {
            tarefa.setTitulo(this.titulo);
        }
        if (this.descricao != null) {
            tarefa.setDescricao(this.descricao);
        }
        if (this.status != null) {
            tarefa.setStatus(StatusTarefa.valueOf(this.status));
        }
        if (this.dataLimite != null) {
            tarefa.setDataLimite(this.dataLimite);
        }
        if (this.idResponsavel != null) {
            PessoaEntity responsavel = new PessoaEntity();
            responsavel.setId(this.idResponsavel);
            tarefa.setResponsavel(responsavel);
        }

        // Atualiza data de conclusão se status for "CONCLUIDA"
        if ("CONCLUIDA".equalsIgnoreCase(this.status)) {
            tarefa.setDataConclusao(LocalDateTime.now());
        }
    }
}