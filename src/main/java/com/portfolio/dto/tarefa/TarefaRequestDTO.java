package com.portfolio.dto.tarefa;

import com.portfolio.model.PessoaEntity;
import com.portfolio.model.ProjetoEntity;
import com.portfolio.model.enumeration.StatusTarefa;
import com.portfolio.model.TarefaEntity;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TarefaRequestDTO {

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
    private String titulo;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @NotNull(message = "ID do projeto é obrigatório")
    private Long idProjeto;

    private Long idResponsavel;

    @NotNull(message = "Status é obrigatório")
    private String status;

    @FutureOrPresent(message = "Data limite deve ser no presente ou futuro")
    private LocalDateTime dataLimite;

    public TarefaEntity toEntity() {
        TarefaEntity tarefa = new TarefaEntity();
        tarefa.setTitulo(this.titulo);
        tarefa.setDescricao(this.descricao);
        tarefa.setStatus(StatusTarefa.valueOf(this.status));
        tarefa.setDataLimite(this.dataLimite);

        // Cria entidades relacionadas apenas com IDs
        if (this.idProjeto != null) {
            ProjetoEntity projeto = new ProjetoEntity();
            projeto.setId(this.idProjeto);
            tarefa.setProjeto(projeto);
        }

        if (this.idResponsavel != null) {
            PessoaEntity responsavel = new PessoaEntity();
            responsavel.setId(this.idResponsavel);
            tarefa.setResponsavel(responsavel);
        }

        return tarefa;
    }
}