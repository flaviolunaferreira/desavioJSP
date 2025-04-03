package com.portfolio.service;

import com.portfolio.dto.projeto.ProjetoRequestDTO;
import com.portfolio.dto.projeto.ProjetoResponseDTO;
import com.portfolio.dto.projeto.ProjetoStatusUpdateDTO;
import java.util.List;

public interface ProjetoService {
    ProjetoResponseDTO criarProjeto(ProjetoRequestDTO projetoDTO);
    ProjetoResponseDTO atualizarProjeto(Long id, ProjetoRequestDTO projetoDTO);
    void excluirProjeto(Long id);
    ProjetoResponseDTO buscarProjetoPorId(Long id);
    List<ProjetoResponseDTO> listarTodosProjetos();
    ProjetoResponseDTO atualizarStatus(Long id, ProjetoStatusUpdateDTO statusDTO);
    String calcularRisco(Long projetoId);
}