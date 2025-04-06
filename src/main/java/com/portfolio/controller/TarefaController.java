package com.portfolio.controller;

import com.portfolio.dto.tarefa.TarefaStatusDTO;
import com.portfolio.dto.tarefa.TarefaRequestDTO;
import com.portfolio.dto.tarefa.TarefaResponseDTO;
import com.portfolio.dto.tarefa.TarefaUpdateDTO;
import com.portfolio.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/tarefas")
@Tag(name = "Tarefas", description = "Gerenciamento de tarefas dos projetos")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    @Operation(summary = "Criar nova tarefa", description = "Registra uma nova tarefa para um projeto", responses = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso", content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos ou responsável não é funcionário"),
                    @ApiResponse(responseCode = "404", description = "Projeto ou responsável não encontrado")})
    public ResponseEntity<TarefaResponseDTO> criarTarefa(
            @Parameter(description = "Dados da tarefa a ser criada", required = true)
            @RequestBody @Valid TarefaRequestDTO request) {
        TarefaResponseDTO response = tarefaService.criarTarefa(request);
        return ResponseEntity.created(buildUri(response)).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarefa por ID",
            description = "Recupera os detalhes completos de uma tarefa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarefa encontrada",
                            content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")})
    public ResponseEntity<TarefaResponseDTO> buscarPorId(
            @Parameter(description = "ID da tarefa", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarTarefaPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar tarefas por projeto",
            description = "Recupera todas as tarefas de um projeto específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada",
                            content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Projeto não encontrado")})
    public ResponseEntity<List<TarefaResponseDTO>> listarPorProjeto(
            @Parameter(description = "ID do projeto", required = true, example = "1")
            @RequestParam Long projetoId) {
        return ResponseEntity.ok(tarefaService.listarTarefasPorProjeto(projetoId));
    }

    @GetMapping("/responsavel/{responsavelId}")
    @Operation(summary = "Listar tarefas por responsável",
            description = "Recupera todas as tarefas atribuídas a um responsável",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada",
                            content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Responsável não encontrado")})
    public ResponseEntity<List<TarefaResponseDTO>> listarPorResponsavel(
            @Parameter(description = "ID do responsável", required = true, example = "1")
            @PathVariable Long responsavelId) {
        return ResponseEntity.ok(tarefaService.listarTarefasPorResponsavel(responsavelId));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar tarefa",
            description = "Atualiza parcialmente os dados de uma tarefa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarefa atualizada",
                            content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Tarefa ou responsável não encontrado")})
    public ResponseEntity<TarefaResponseDTO> atualizarTarefa(
            @Parameter(description = "ID da tarefa", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Dados parciais para atualização", required = true)
            @RequestBody @Valid TarefaUpdateDTO request) {
        return ResponseEntity.ok(tarefaService.atualizarTarefa(id, request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da tarefa",
            description = "Realiza a transição de status de uma tarefa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status atualizado",
                            content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Transição de status inválida"),
                    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")})
    public ResponseEntity<TarefaResponseDTO> atualizarStatus(
            @Parameter(description = "ID da tarefa", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Novo status e comentários", required = true)
            @RequestBody @Valid TarefaStatusDTO request) {
        return ResponseEntity.ok(tarefaService.atualizarStatus(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tarefa",
            description = "Remove uma tarefa (apenas se estiver com status PENDENTE)",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tarefa excluída"),
                    @ApiResponse(responseCode = "400", description = "Exclusão não permitida pelo status"),
                    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")})
    public ResponseEntity<Void> excluirTarefa(
            @Parameter(description = "ID da tarefa", required = true, example = "1")
            @PathVariable Long id) {
        tarefaService.excluirTarefa(id);
        return ResponseEntity.noContent().build();
    }

    URI buildUri(TarefaResponseDTO response) {
        return URI.create("/api/tarefas/" + response.getId());
    }
}