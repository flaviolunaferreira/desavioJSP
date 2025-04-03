package com.portfolio.dto.projeto;

import com.portfolio.model.ProjetoEntity;
import com.portfolio.model.enumeration.StatusProjeto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjetoStatusUpdateDTO {
    @NotNull(message = "Novo status é obrigatório")
    private String novoStatus;

    private String motivoCancelamento;

    public void applyToEntity(ProjetoEntity projeto) {
        projeto.setStatus(StatusProjeto.valueOf(this.novoStatus));
        if ("CANCELADO".equalsIgnoreCase(this.novoStatus)) {
            projeto.setDescricao(projeto.getDescricao() +
                    "\nMotivo do cancelamento: " + this.motivoCancelamento);
        }
    }
}