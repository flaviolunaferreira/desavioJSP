package com.portfolio.dto.membro;

import com.portfolio.model.MembroEntity;
import lombok.Data;

@Data
public class MembroIdDTO {
    private Long idProjeto;
    private Long idPessoa;

    public static MembroIdDTO fromEntity(MembroEntity membro) {
        MembroIdDTO dto = new MembroIdDTO();
        dto.setIdProjeto(membro.getProjeto().getId());
        dto.setIdPessoa(membro.getPessoa().getId());
        return dto;
    }
}