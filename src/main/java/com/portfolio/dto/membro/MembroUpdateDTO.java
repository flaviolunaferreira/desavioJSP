package com.portfolio.dto.membro;

import com.portfolio.model.MembroEntity;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MembroUpdateDTO {

    private LocalDate dataEntrada;

    @Size(max = 50, message = "Função deve ter no máximo 50 caracteres")
    private String funcao;

    public void applyToEntity(MembroEntity membro) {
        if (this.dataEntrada != null) {
            membro.setDataEntrada(this.dataEntrada);
        }
        if (this.funcao != null) {
            membro.setFuncao(this.funcao);
        }
    }
}