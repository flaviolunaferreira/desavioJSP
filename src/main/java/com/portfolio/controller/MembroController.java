package com.portfolio.controller;

import com.portfolio.dto.membro.MembroRequestDTO;
import com.portfolio.dto.membro.MembroResponseDTO;
import com.portfolio.dto.membro.MembroUpdateDTO;
import com.portfolio.service.MembroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/membros")
@Tag(name = "Membros", description = "Gerenciamento de membros dos projetos")
public class MembroController {

    private final MembroService membroService;

    public MembroController(MembroService membroService) {
        this.membroService = membroService;
    }

    @PostMapping
    @Operation(summary = "Associar membro a projeto",
            description = "Cria uma associação entre uma pessoa e um projeto",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Membro associado com sucesso",
                            content = @Content(schema = @Schema(implementation = MembroResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
                    @ApiResponse(responseCode = "404", description = "Projeto ou pessoa não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Membro já associado ao projeto")})
    public ResponseEntity<MembroResponseDTO> associarMembro(
            @Parameter(description = "Dados da associação membro-projeto", required = true)
            @RequestBody @Valid MembroRequestDTO request) {
        MembroResponseDTO response = membroService.associarMembro(request);
        return ResponseEntity.created(buildUri(response)).body(response);
    }

    @DeleteMapping("/{projetoId}/{pessoaId}")
    @Operation(summary = "Desassociar membro de projeto",
            description = "Remove a associação entre uma pessoa e um projeto",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Membro desassociado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Associação não encontrada")})
    public ResponseEntity<Void> desassociarMembro(
            @Parameter(description = "ID do projeto", required = true, example = "1")
            @PathVariable Long projetoId,
            @Parameter(description = "ID da pessoa", required = true, example = "1")
            @PathVariable Long pessoaId) {
        membroService.desassociarMembro(projetoId, pessoaId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{projetoId}/{pessoaId}")
    @Operation(summary = "Atualizar função do membro",
            description = "Atualiza a função de um membro em um projeto específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Função atualizada",
                            content = @Content(schema = @Schema(implementation = MembroResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Associação não encontrada")})
    public ResponseEntity<MembroResponseDTO> atualizarFuncao(
            @Parameter(description = "ID do projeto", required = true, example = "1")
            @PathVariable Long projetoId,
            @Parameter(description = "ID da pessoa", required = true, example = "1")
            @PathVariable Long pessoaId,
            @Parameter(description = "Dados atualizados da função", required = true)
            @RequestBody @Valid MembroUpdateDTO updateDTO) {
        return ResponseEntity.ok(membroService.atualizarFuncao(projetoId, pessoaId, updateDTO));
    }

    @GetMapping("/{projetoId}/{pessoaId}")
    @Operation(summary = "Buscar associação específica",
            description = "Recupera os detalhes de uma associação membro-projeto específica",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Associação encontrada",
                            content = @Content(schema = @Schema(implementation = MembroResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Associação não encontrada")})
    public ResponseEntity<MembroResponseDTO> buscarAssociacao(
            @Parameter(description = "ID do projeto", required = true, example = "1")
            @PathVariable Long projetoId,
            @Parameter(description = "ID da pessoa", required = true, example = "1")
            @PathVariable Long pessoaId) {
        return ResponseEntity.ok(membroService.buscarAssociacao(projetoId, pessoaId));
    }

    @GetMapping("/projeto/{projetoId}")
    @Operation(summary = "Listar membros por projeto",
            description = "Recupera todos os membros associados a um projeto específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de membros retornada",
                            content = @Content(schema = @Schema(implementation = MembroResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Projeto não encontrado")})
    public ResponseEntity<List<MembroResponseDTO>> listarMembrosPorProjeto(
            @Parameter(description = "ID do projeto", required = true, example = "1")
            @PathVariable Long projetoId) {
        return ResponseEntity.ok(membroService.listarMembrosPorProjeto(projetoId));
    }

    @GetMapping("/pessoa/{pessoaId}")
    @Operation(summary = "Listar projetos por pessoa",
            description = "Recupera todos os projetos associados a uma pessoa específica",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de projetos retornada",
                            content = @Content(schema = @Schema(implementation = MembroResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")})
    public ResponseEntity<List<MembroResponseDTO>> listarProjetosDaPessoa(
            @Parameter(description = "ID da pessoa", required = true, example = "1")
            @PathVariable Long pessoaId) {
        return ResponseEntity.ok(membroService.listarProjetosDaPessoa(pessoaId));
    }

    private URI buildUri(MembroResponseDTO response) {
        return URI.create("/api/membros/" + response.getProjeto().getId() + "/" + response.getPessoa().getId());
    }
}