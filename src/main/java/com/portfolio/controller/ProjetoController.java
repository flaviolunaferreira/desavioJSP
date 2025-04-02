package com.portfolio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/projetos")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProjetoController {

    /**
     * Listar todos os projetos
     * @return ResponseEntity<List<ProjetoResponseDTO>>
     */
    @GetMapping("/listar")
    public RespnseEntity<List<?>> listarProjetos() {
        return null;
    }

    /**
     * Exibe formulário para adicionar/editar um novo projeto
     * @return String
     */
    @GetMapping("/form")
    public String showProjectForm() {
        return null;
    }

    /**
     * Adiciona um novo projeto
     * @param projetoRequestDTO
     * @return ResponseEntity<ProjetoResponseDTO>
     */
    @PostMapping("/")
    public ResponseEntity<?> adicionarProjeto(@RequestBody ProjetoRequestDTO projetoRequestDTO) {
        return null;
    }

    /**
     * Edita um projeto existente
     * @param id
     * @param projetoRequestDTO
     * @return ResponseEntity<ProjetoResponseDTO>
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editarProjeto(@PathVariable Long id, @RequestBody ProjetoRequestDTO projetoRequestDTO) {
        return null;
    }

    /**
     * Apaga um projeto existente
     * @param id
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProjeto(@PathVariable Long id) {
        return null;
    }
}
