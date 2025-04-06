package com.portfolio.controller;

import com.portfolio.dto.tarefa.TarefaRequestDTO;
import com.portfolio.dto.tarefa.TarefaResponseDTO;
import com.portfolio.dto.tarefa.TarefaStatusDTO;
import com.portfolio.dto.tarefa.TarefaUpdateDTO;
import com.portfolio.service.TarefaService;
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

class TarefaControllerTest {

    @Mock
    private TarefaService tarefaService;

    @InjectMocks
    private TarefaController tarefaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarTarefa_DeveRetornarCreated() {
        TarefaRequestDTO request = new TarefaRequestDTO();
        TarefaResponseDTO response = new TarefaResponseDTO();
        response.setId(1L);

        when(tarefaService.criarTarefa(request)).thenReturn(response);

        ResponseEntity<TarefaResponseDTO> result = tarefaController.criarTarefa(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(tarefaService, times(1)).criarTarefa(request);
    }

    @Test
    void buscarPorId_DeveRetornarOk() {
        TarefaResponseDTO response = new TarefaResponseDTO();

        when(tarefaService.buscarTarefaPorId(1L)).thenReturn(response);

        ResponseEntity<TarefaResponseDTO> result = tarefaController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(tarefaService, times(1)).buscarTarefaPorId(1L);
    }

    @Test
    void listarPorProjeto_DeveRetornarOk() {
        List<TarefaResponseDTO> response = Collections.singletonList(new TarefaResponseDTO());

        when(tarefaService.listarTarefasPorProjeto(1L)).thenReturn(response);

        ResponseEntity<List<TarefaResponseDTO>> result = tarefaController.listarPorProjeto(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(tarefaService, times(1)).listarTarefasPorProjeto(1L);
    }

    @Test
    void listarPorResponsavel_DeveRetornarOk() {
        List<TarefaResponseDTO> response = Collections.singletonList(new TarefaResponseDTO());

        when(tarefaService.listarTarefasPorResponsavel(1L)).thenReturn(response);

        ResponseEntity<List<TarefaResponseDTO>> result = tarefaController.listarPorResponsavel(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(tarefaService, times(1)).listarTarefasPorResponsavel(1L);
    }

    @Test
    void atualizarTarefa_DeveRetornarOk() {
        TarefaUpdateDTO request = new TarefaUpdateDTO();
        TarefaResponseDTO response = new TarefaResponseDTO();

        when(tarefaService.atualizarTarefa(1L, request)).thenReturn(response);

        ResponseEntity<TarefaResponseDTO> result = tarefaController.atualizarTarefa(1L, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(tarefaService, times(1)).atualizarTarefa(1L, request);
    }

    @Test
    void atualizarStatus_DeveRetornarOk() {
        TarefaStatusDTO request = new TarefaStatusDTO();
        TarefaResponseDTO response = new TarefaResponseDTO();

        when(tarefaService.atualizarStatus(1L, request)).thenReturn(response);

        ResponseEntity<TarefaResponseDTO> result = tarefaController.atualizarStatus(1L, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(tarefaService, times(1)).atualizarStatus(1L, request);
    }

    @Test
    void excluirTarefa_DeveRetornarNoContent() {
        ResponseEntity<Void> result = tarefaController.excluirTarefa(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(tarefaService, times(1)).excluirTarefa(1L);
    }

    @Test
    void buildUri_DeveRetornarUriCorreta() {
        TarefaResponseDTO response = new TarefaResponseDTO();
        response.setId(1L);

        URI uri = tarefaController.buildUri(response);

        assertEquals("/api/tarefas/1", uri.toString());
    }
}