package com.portfolio.controller;

import com.portfolio.dto.pessoa.PessoaRequestDTO;
import com.portfolio.dto.pessoa.PessoaResponseDTO;
import com.portfolio.dto.pessoa.PessoaUpdateDTO;
import com.portfolio.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaControllerTest {

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaController pessoaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarPessoa_DeveRetornarCreated() {
        PessoaRequestDTO request = new PessoaRequestDTO();
        PessoaResponseDTO response = new PessoaResponseDTO();
        response.setId(1L);

        when(pessoaService.criarPessoa(request)).thenReturn(response);

        ResponseEntity<PessoaResponseDTO> result = pessoaController.criarPessoa(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(pessoaService, times(1)).criarPessoa(request);
    }

    @Test
    void buscarPorId_DeveRetornarOk() {
        PessoaResponseDTO response = new PessoaResponseDTO();

        when(pessoaService.buscarPessoaPorId(1L)).thenReturn(response);

        ResponseEntity<PessoaResponseDTO> result = pessoaController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(pessoaService, times(1)).buscarPessoaPorId(1L);
    }

    @Test
    void listarTodos_DeveRetornarOk() {
        List<PessoaResponseDTO> response = Collections.singletonList(new PessoaResponseDTO());

        when(pessoaService.listarTodasPessoas()).thenReturn(response);

        ResponseEntity<List<PessoaResponseDTO>> result = pessoaController.listarTodos();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(pessoaService, times(1)).listarTodasPessoas();
    }

    @Test
    void listarFuncionarios_DeveRetornarOk() {
        List<PessoaResponseDTO> response = Collections.singletonList(new PessoaResponseDTO());

        when(pessoaService.listarFuncionarios()).thenReturn(response);

        ResponseEntity<List<PessoaResponseDTO>> result = pessoaController.listarFuncionarios();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(pessoaService, times(1)).listarFuncionarios();
    }

    @Test
    void listarGerentes_DeveRetornarOk() {
        List<PessoaResponseDTO> response = Collections.singletonList(new PessoaResponseDTO());

        when(pessoaService.listarGerentes()).thenReturn(response);

        ResponseEntity<List<PessoaResponseDTO>> result = pessoaController.listarGerentes();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(pessoaService, times(1)).listarGerentes();
    }

    @Test
    void atualizarPessoa_DeveRetornarOk() {
        PessoaUpdateDTO request = new PessoaUpdateDTO();
        PessoaResponseDTO response = new PessoaResponseDTO();

        when(pessoaService.atualizarPessoa(1L, request)).thenReturn(response);

        ResponseEntity<PessoaResponseDTO> result = pessoaController.atualizarPessoa(1L, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(pessoaService, times(1)).atualizarPessoa(1L, request);
    }

    @Test
    void excluirPessoa_DeveRetornarNoContent() {
        ResponseEntity<Void> result = pessoaController.excluirPessoa(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(pessoaService, times(1)).excluirPessoa(1L);
    }

    @Test
    void buildUri_DeveRetornarUriCorreta() {
        PessoaResponseDTO response = new PessoaResponseDTO();
        response.setId(1L);

        URI uri = pessoaController.buildUri(response);

        assertEquals("/api/pessoas/1", uri.toString());
    }
}