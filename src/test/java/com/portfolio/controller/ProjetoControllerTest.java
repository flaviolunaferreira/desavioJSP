package com.portfolio.controller;

import com.portfolio.dto.projeto.ProjetoRequestDTO;
import com.portfolio.dto.projeto.ProjetoResponseDTO;
import com.portfolio.dto.projeto.ProjetoStatusUpdateDTO;
import com.portfolio.service.ProjetoService;
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

class ProjetoControllerTest {

    @Mock
    private ProjetoService projetoService;

    @InjectMocks
    private ProjetoController projetoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarProjeto_DeveRetornarCreated() {
        ProjetoRequestDTO request = new ProjetoRequestDTO();
        ProjetoResponseDTO response = new ProjetoResponseDTO();
        response.setId(1L);

        when(projetoService.criarProjeto(request)).thenReturn(response);

        ResponseEntity<ProjetoResponseDTO> result = projetoController.criarProjeto(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(projetoService, times(1)).criarProjeto(request);
    }

    @Test
    void buscarPorId_DeveRetornarOk() {
        ProjetoResponseDTO response = new ProjetoResponseDTO();

        when(projetoService.buscarProjetoPorId(1L)).thenReturn(response);

        ResponseEntity<ProjetoResponseDTO> result = projetoController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(projetoService, times(1)).buscarProjetoPorId(1L);
    }

    @Test
    void listarTodos_DeveRetornarOk() {
        List<ProjetoResponseDTO> response = Collections.singletonList(new ProjetoResponseDTO());

        when(projetoService.listarTodosProjetos()).thenReturn(response);

        ResponseEntity<List<ProjetoResponseDTO>> result = projetoController.listarTodos();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(projetoService, times(1)).listarTodosProjetos();
    }

    @Test
    void atualizarProjeto_DeveRetornarOk() {
        ProjetoRequestDTO request = new ProjetoRequestDTO();
        ProjetoResponseDTO response = new ProjetoResponseDTO();

        when(projetoService.atualizarProjeto(1L, request)).thenReturn(response);

        ResponseEntity<ProjetoResponseDTO> result = projetoController.atualizarProjeto(1L, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(projetoService, times(1)).atualizarProjeto(1L, request);
    }

    @Test
    void atualizarStatus_DeveRetornarOk() {
        ProjetoStatusUpdateDTO request = new ProjetoStatusUpdateDTO();
        ProjetoResponseDTO response = new ProjetoResponseDTO();

        when(projetoService.atualizarStatus(1L, request)).thenReturn(response);

        ResponseEntity<ProjetoResponseDTO> result = projetoController.atualizarStatus(1L, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(projetoService, times(1)).atualizarStatus(1L, request);
    }

    @Test
    void calcularRisco_DeveRetornarOk() {
        when(projetoService.calcularRisco(1L)).thenReturn("BAIXO");

        ResponseEntity<String> result = projetoController.calcularRisco(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("BAIXO", result.getBody());
        verify(projetoService, times(1)).calcularRisco(1L);
    }

    @Test
    void excluirProjeto_DeveRetornarNoContent() {
        ResponseEntity<Void> result = projetoController.excluirProjeto(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(projetoService, times(1)).excluirProjeto(1L);
    }

    @Test
    void buildUri_DeveRetornarUriCorreta() {
        ProjetoResponseDTO response = new ProjetoResponseDTO();
        response.setId(1L);

        URI uri = projetoController.buildUri(response);

        assertEquals("/api/projetos/1", uri.toString());
    }
}