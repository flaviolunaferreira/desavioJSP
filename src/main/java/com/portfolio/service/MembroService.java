package com.portfolio.service;

import com.portfolio.dto.membro.MembroRequestDTO;
import com.portfolio.dto.membro.MembroResponseDTO;
import com.portfolio.dto.membro.MembroUpdateDTO;

import java.util.List;

public interface MembroService {
    MembroResponseDTO associarMembro(MembroRequestDTO membroDTO);
    void desassociarMembro(Long projetoId, Long pessoaId);
    MembroResponseDTO atualizarFuncao(Long projetoId, Long pessoaId, MembroUpdateDTO updateDTO);
    MembroResponseDTO buscarAssociacao(Long projetoId, Long pessoaId);
    List<MembroResponseDTO> listarMembrosPorProjeto(Long projetoId);
    List<MembroResponseDTO> listarProjetosDaPessoa(Long pessoaId);
}
