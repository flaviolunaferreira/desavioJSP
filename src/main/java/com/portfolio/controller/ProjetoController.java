package com.portfolio.controller;

import com.portfolio.dto.projeto.ProjetoRequestDTO;
import com.portfolio.dto.projeto.ProjetoResponseDTO;
import com.portfolio.exception.ProjetoNotDeleteException;
import com.portfolio.service.ProjetoService.ProjetoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjetoController {

    private ProjetoService projetoService;

    /**
     * Lista todos os projetos
     * @return lista todos os projetos cadastrados.
     */
    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> listarProjetos() {
        return ResponseEntity.ok().body(projetoService.listarProjetos());
    }

    /**
     * Busca um projeto pelo id
     * @param projetoRequestDTO com os dados do projeto
     * @return ProjetoResponseDTO
     */
    @PostMapping
    public ResponseEntity<ProjetoResponseDTO> saveProjeto(@ModelAttribute ProjetoRequestDTO projetoRequestDTO) {
        return ResponseEntity.ok().body(projetoService.save(projetoRequestDTO));
    }

    /**
     * Atualiza um projeto
     * @param projetoRequestDTO, idProjeto
     * @return ProjetoResponseDTO
     */
    @PutMapping
    public ResponseEntity<ProjetoResponseDTO> updateProjeto(@ModelAttribute ProjetoRequestDTO projetoRequestDTO, Long idProjeto) {
        return ResponseEntity.ok().body(projetoService.update(projetoRequestDTO, idProjeto));
    }

    /**
     * Apaga um projeto pelo id
     * @param id do projeto que deseja apagar
     */
    @GetMapping("/delete/{id}")
    public void deleteProject(@PathVariable Long id) throws ProjetoNotDeleteException {
        projetoService.delete(id);
    }
}