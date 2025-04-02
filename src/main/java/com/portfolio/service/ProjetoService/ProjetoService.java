package com.portfolio.service.ProjetoService;

import com.portfolio.dto.projeto.ProjetoRequestDTO;
import com.portfolio.dto.projeto.ProjetoResponseDTO;
import com.portfolio.model.ProjetoEntity;

import java.util.List;

public interface ProjetoService {

    List<ProjetoResponseDTO> listarProjetos();

    ProjetoEntity findById(Long id);

    void delete(Long id);

    ProjetoResponseDTO save(ProjetoRequestDTO projetoRequestDTO);

    ProjetoResponseDTO update(ProjetoRequestDTO projetoRequestDTO, Long idProjeto);
}
