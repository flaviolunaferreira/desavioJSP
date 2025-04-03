package com.portfolio.dto.projeto;

import com.portfolio.model.ProjetoEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProjetoResponseDTO {
    private Long id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataPrevisaoFim;
    private LocalDate dataFim;
    private String descricao;
    private String status;
    private BigDecimal orcamento;
    private String risco;
    private GerenteInfoDTO gerente;

    @Data
    public static class GerenteInfoDTO {
        private Long id;
        private String nome;
    }

    public static ProjetoResponseDTO fromEntity(ProjetoEntity projeto) {
        ProjetoResponseDTO dto = new ProjetoResponseDTO();
        dto.setId(projeto.getId());
        dto.setNome(projeto.getNome());
        dto.setDataInicio(projeto.getDataInicio());
        dto.setDataPrevisaoFim(projeto.getDataPrevisaoFim());
        dto.setDataFim(projeto.getDataFim());
        dto.setDescricao(projeto.getDescricao());
        dto.setStatus(String.valueOf(projeto.getStatus()));
        dto.setOrcamento(projeto.getOrcamento());
        dto.setRisco(String.valueOf(projeto.getRisco()));

        if (projeto.getGerente() != null) {
            GerenteInfoDTO gerenteInfo = new GerenteInfoDTO();
            gerenteInfo.setId(projeto.getGerente().getId());
            gerenteInfo.setNome(projeto.getGerente().getNome());
            dto.setGerente(gerenteInfo);
        }

        return dto;
    }
}