package com.portfolio.controller;

import com.portfolio.dto.projeto.ProjetoRequestDTO;
import com.portfolio.dto.projeto.ProjetoResponseDTO;
import com.portfolio.dto.projeto.ProjetoStatusUpdateDTO;
import com.portfolio.service.ProjetoService;
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
@RequestMapping("/api/projetos")
@Tag(name = "Projetos", description = "Gerenciamento do portfólio de projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @PostMapping
    @Operation(summary = "Criar novo projeto",
            description = "Registra um novo projeto no portfólio",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Projeto criado com sucesso",
                            content = @Content(schema = @Schema(implementation = ProjetoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
                    @ApiResponse(responseCode = "409", description = "Conflito (nome já existe)")})
    public ResponseEntity<ProjetoResponseDTO> criarProjeto(
            @Parameter(description = "Dados do projeto a ser criado", required = true)
            @RequestBody @Valid ProjetoRequestDTO request) {
        ProjetoResponseDTO response = projetoService.criarProjeto(request);
        return ResponseEntity.created(buildUri(response)).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar projeto por ID",
            description = "Recupera os detalhes de um projeto específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Projeto encontrado",
                            content = @Content(schema = @Schema(implementation = ProjetoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Projeto não encontrado")})
    public ResponseEntity<ProjetoResponseDTO> buscarPorId(
            @Parameter(description = "ID do projeto a ser buscado", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(projetoService.buscarProjetoPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os projetos",
            description = "Recupera todos os projetos do portfólio",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de projetos retornada",
                            content = @Content(schema = @Schema(implementation = ProjetoResponseDTO.class)))
            })
    public ResponseEntity<List<ProjetoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(projetoService.listarTodosProjetos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar projeto",
            description = "Atualiza todos os dados de um projeto existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Projeto atualizado",
                            content = @Content(schema = @Schema(implementation = ProjetoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Conflito (nome já existe")})
    public ResponseEntity<ProjetoResponseDTO> atualizarProjeto(
            @Parameter(description = "ID do projeto a ser atualizado", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do projeto", required = true)
            @RequestBody @Valid ProjetoRequestDTO request) {
        return ResponseEntity.ok(projetoService.atualizarProjeto(id, request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do projeto",
            description = "Realiza a transição de status do projeto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status atualizado",
                            content = @Content(schema = @Schema(implementation = ProjetoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Transição de status inválida"),
                    @ApiResponse(responseCode = "404", description = "Projeto não encontrado")})
    public ResponseEntity<ProjetoResponseDTO> atualizarStatus(
            @Parameter(description = "ID do projeto", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Novo status do projeto", required = true)
            @RequestBody @Valid ProjetoStatusUpdateDTO request) {
        return ResponseEntity.ok(projetoService.atualizarStatus(id, request));
    }

    @GetMapping("/{id}/risco")
    @Operation(summary = "Calcular risco do projeto", description = "Calcula o nível de risco do projeto baseado no cronograma",
            responses = { @ApiResponse(responseCode = "200", description = "Risco calculado",
                          content = @Content(schema = @Schema(implementation = String.class))),
                          @ApiResponse(responseCode = "404", description = "Projeto não encontrado")})
    public ResponseEntity<String> calcularRisco( @Parameter(description = "ID do projeto", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(projetoService.calcularRisco(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir projeto", description = "Remove um projeto do portfólio (apenas se status permitir)",
            responses = { @ApiResponse(responseCode = "204", description = "Projeto excluído"),
                          @ApiResponse(responseCode = "400", description = "Exclusão não permitida pelo status"),
                          @ApiResponse(responseCode = "404", description = "Projeto não encontrado")})
    public ResponseEntity<Void> excluirProjeto(@Parameter(description = "ID do projeto a ser excluído",
            required = true, example = "1") @PathVariable Long id) {
        projetoService.excluirProjeto(id);
        return ResponseEntity.noContent().build();
    }

    private URI buildUri(ProjetoResponseDTO response) {
        return URI.create("/api/projetos/" + response.getId());
    }
}