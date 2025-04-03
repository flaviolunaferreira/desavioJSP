package com.portfolio.service;

import com.portfolio.dto.tarefa.TarefaStatusDTO;
import com.portfolio.dto.tarefa.TarefaRequestDTO;
import com.portfolio.dto.tarefa.TarefaResponseDTO;
import com.portfolio.dto.tarefa.TarefaUpdateDTO;

import java.util.List;

public interface TarefaService {
    TarefaResponseDTO criarTarefa(TarefaRequestDTO tarefaDTO);
    TarefaResponseDTO atualizarTarefa(Long id, TarefaUpdateDTO tarefaDTO);
    void excluirTarefa(Long id);
    TarefaResponseDTO buscarTarefaPorId(Long id);
    List<TarefaResponseDTO> listarTarefasPorProjeto(Long projetoId);
    List<TarefaResponseDTO> listarTarefasPorResponsavel(Long responsavelId);
    TarefaResponseDTO atualizarStatus(Long id, TarefaStatusDTO statusDTO);
}