package com.portfolio.dto;

import com.portfolio.model.enumeration.StatusTarefa;
import com.portfolio.model.TarefaEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TarefaStatusDTO {

    @NotNull(message = "Novo status é obrigatório")
    private String novoStatus;

    private String comentario;

    public void applyToEntity(TarefaEntity tarefa) {
        tarefa.setStatus(StatusTarefa.valueOf(this.novoStatus));
        if (this.comentario != null && !this.comentario.isBlank()) {
            tarefa.setDescricao(tarefa.getDescricao() + "\nComentário: " + this.comentario);
        }
        if ("CONCLUIDA".equalsIgnoreCase(this.novoStatus)) {
            tarefa.setDataConclusao(LocalDateTime.now());
        }
    }
}