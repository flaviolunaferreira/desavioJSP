package com.portfolio.service.ProjetoService.Impl;

import com.portfolio.dto.projeto.ProjetoRequestDTO;
import com.portfolio.dto.projeto.ProjetoResponseDTO;
import com.portfolio.model.ProjetoEntity;
import com.portfolio.service.ProjetoService.ProjetoService;

import java.util.List;

public class ProjetoServiceImpl implements ProjetoService {
    @Override
    public List<ProjetoResponseDTO> listarProjetos() {
        return List.of();
    }

    @Override
    public ProjetoEntity findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public ProjetoResponseDTO save(ProjetoRequestDTO projetoRequestDTO) {
        return null;
    }

    @Override
    public ProjetoResponseDTO update(ProjetoRequestDTO projetoRequestDTO, Long idProjeto) {
        return null;
    }
}
