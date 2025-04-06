package com.portfolio.controller;

import com.portfolio.dto.membro.MembroRequestDTO;
import com.portfolio.dto.membro.MembroResponseDTO;
import com.portfolio.dto.membro.MembroUpdateDTO;
import com.portfolio.service.MembroService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MembroControllerTest {

    @Mock
    private MembroService membroService;

    @InjectMocks
    private MembroController membroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void associarMembro_DeveRetornarCreated() {
        MembroRequestDTO request = new MembroRequestDTO();
        request.setIdProjeto(1L);
        request.setIdPessoa(1L);
        request.setDataEntrada(LocalDate.now());

        // Crie um response com todos os campos necessários
        MembroResponseDTO response = new MembroResponseDTO();
        MembroResponseDTO.ProjetoInfoDTO projetoInfo = new MembroResponseDTO.ProjetoInfoDTO();
        projetoInfo.setId(1L);
        MembroResponseDTO.PessoaInfoDTO pessoaInfo = new MembroResponseDTO.PessoaInfoDTO();
        pessoaInfo.setId(1L);
        response.setProjeto(projetoInfo);
        response.setPessoa(pessoaInfo);

        when(membroService.associarMembro(request)).thenReturn(response);

        ResponseEntity<MembroResponseDTO> result = membroController.associarMembro(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(membroService, times(1)).associarMembro(request);
    }

    @Test
    void desassociarMembro_DeveRetornarNoContent() {
        ResponseEntity<Void> result = membroController.desassociarMembro(1L, 1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(membroService, times(1)).desassociarMembro(1L, 1L);
    }

    @Test
    void atualizarFuncao_DeveRetornarOk() {
        MembroUpdateDTO updateDTO = new MembroUpdateDTO();
        MembroResponseDTO response = new MembroResponseDTO();

        when(membroService.atualizarFuncao(1L, 1L, updateDTO)).thenReturn(response);

        ResponseEntity<MembroResponseDTO> result = membroController.atualizarFuncao(1L, 1L, updateDTO);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(membroService, times(1)).atualizarFuncao(1L, 1L, updateDTO);
    }

    @Test
    void buscarAssociacao_DeveRetornarOk() {
        MembroResponseDTO response = new MembroResponseDTO();

        when(membroService.buscarAssociacao(1L, 1L)).thenReturn(response);

        ResponseEntity<MembroResponseDTO> result = membroController.buscarAssociacao(1L, 1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(membroService, times(1)).buscarAssociacao(1L, 1L);
    }

    @Test
    void listarMembrosPorProjeto_DeveRetornarOk() {
        List<MembroResponseDTO> response = Collections.singletonList(new MembroResponseDTO());

        when(membroService.listarMembrosPorProjeto(1L)).thenReturn(response);

        ResponseEntity<List<MembroResponseDTO>> result = membroController.listarMembrosPorProjeto(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(membroService, times(1)).listarMembrosPorProjeto(1L);
    }

    @Test
    void listarProjetosDaPessoa_DeveRetornarOk() {
        List<MembroResponseDTO> response = Collections.singletonList(new MembroResponseDTO());

        when(membroService.listarProjetosDaPessoa(1L)).thenReturn(response);

        ResponseEntity<List<MembroResponseDTO>> result = membroController.listarProjetosDaPessoa(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(membroService, times(1)).listarProjetosDaPessoa(1L);
    }

    @Test
    void buildUri_DeveRetornarUriCorreta() {
        MembroResponseDTO response = new MembroResponseDTO();
        MembroResponseDTO.ProjetoInfoDTO projeto = new MembroResponseDTO.ProjetoInfoDTO();
        projeto.setId(1L);
        MembroResponseDTO.PessoaInfoDTO pessoa = new MembroResponseDTO.PessoaInfoDTO();
        pessoa.setId(2L);
        response.setProjeto(projeto);
        response.setPessoa(pessoa);

        URI uri = membroController.buildUri(response);

        assertEquals("/api/membros/1/2", uri.toString());
    }
}