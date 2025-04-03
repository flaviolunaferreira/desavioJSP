package com.portfolio.controller;

import com.portfolio.dto.pessoa.PessoaRequestDTO;
import com.portfolio.dto.pessoa.PessoaResponseDTO;
import com.portfolio.dto.pessoa.PessoaUpdateDTO;
import com.portfolio.service.PessoaService;
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
@RequestMapping("/api/pessoas")
@Tag(name = "Pessoas", description = "Gerenciamento de membros da equipe")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova pessoa",
            description = "Registra um novo membro na equipe",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pessoa cadastrada com sucesso",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos ou CPF já cadastrado"),
                    @ApiResponse(responseCode = "409", description = "Conflito de cargo (gerente/funcionário")})
    public ResponseEntity<PessoaResponseDTO> criarPessoa(
            @Parameter(description = "Dados da pessoa a ser cadastrada", required = true)
            @RequestBody @Valid PessoaRequestDTO request) {
        PessoaResponseDTO response = pessoaService.criarPessoa(request);
        return ResponseEntity.created(buildUri(response)).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pessoa por ID",
            description = "Recupera os dados completos de uma pessoa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pessoa encontrada",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")})
    public ResponseEntity<PessoaResponseDTO> buscarPorId(
            @Parameter(description = "ID da pessoa", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(pessoaService.buscarPessoaPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas as pessoas",
            description = "Recupera todos os membros cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pessoas retornada",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class)))
            })
    public ResponseEntity<List<PessoaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pessoaService.listarTodasPessoas());
    }

    @GetMapping("/funcionarios")
    @Operation(summary = "Listar funcionários",
            description = "Recupera apenas os membros com cargo de funcionário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de funcionários retornada",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class)))
            })
    public ResponseEntity<List<PessoaResponseDTO>> listarFuncionarios() {
        return ResponseEntity.ok(pessoaService.listarFuncionarios());
    }

    @GetMapping("/gerentes")
    @Operation(summary = "Listar gerentes",
            description = "Recupera apenas os membros com cargo de gerente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de gerentes retornada",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class)))
            })
    public ResponseEntity<List<PessoaResponseDTO>> listarGerentes() {
        return ResponseEntity.ok(pessoaService.listarGerentes());
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar dados da pessoa",
            description = "Atualiza parcialmente os dados de um membro",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dados atualizados",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
                    @ApiResponse(responseCode = "409", description = "Conflito de cargo")})
    public ResponseEntity<PessoaResponseDTO> atualizarPessoa(
            @Parameter(description = "ID da pessoa", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Dados parciais para atualização", required = true)
            @RequestBody @Valid PessoaUpdateDTO request) {
        return ResponseEntity.ok(pessoaService.atualizarPessoa(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover pessoa",
            description = "Remove um membro da equipe (se não estiver vinculado a projetos)",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pessoa removida"),
                    @ApiResponse(responseCode = "400", description = "Não permitido (vinculado a projetos)"),
                    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")})
    public ResponseEntity<Void> excluirPessoa(
            @Parameter(description = "ID da pessoa", required = true, example = "1")
            @PathVariable Long id) {
        pessoaService.excluirPessoa(id);
        return ResponseEntity.noContent().build();
    }

    private URI buildUri(PessoaResponseDTO response) {
        return URI.create("/api/pessoas/" + response.getId());
    }
}