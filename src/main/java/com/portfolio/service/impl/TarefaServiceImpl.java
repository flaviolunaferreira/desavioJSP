package com.portfolio.service.impl;

import com.portfolio.dto.*;
import com.portfolio.dto.TarefaStatusDTO;
import com.portfolio.dto.tarefa.TarefaRequestDTO;
import com.portfolio.dto.tarefa.TarefaResponseDTO;
import com.portfolio.dto.tarefa.TarefaUpdateDTO;
import com.portfolio.exception.*;
import com.portfolio.model.*;
import com.portfolio.model.enumeration.StatusTarefa;
import com.portfolio.repository.*;
import com.portfolio.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository tarefaRepository;
    private final ProjetoRepository projetoRepository;
    private final PessoaRepository pessoaRepository;

    @Override
    @Transactional
    public TarefaResponseDTO criarTarefa(TarefaRequestDTO tarefaDTO) {
        validarNovaTarefa(tarefaDTO);

        TarefaEntity tarefa = tarefaDTO.toEntity();
        tarefa.setProjeto(buscarProjetoValido(tarefaDTO.getIdProjeto()));

        if (tarefaDTO.getIdResponsavel() != null) {
            tarefa.setResponsavel(buscarPessoaValida(tarefaDTO.getIdResponsavel()));
        }

        tarefa.setStatus(StatusTarefa.valueOf("PENDENTE"));
        TarefaEntity tarefaSalva = tarefaRepository.save(tarefa);

        return TarefaResponseDTO.fromEntity(tarefaSalva);
    }

    @Override
    @Transactional
    public TarefaResponseDTO atualizarTarefa(Long id, TarefaUpdateDTO tarefaDTO) {
        TarefaEntity tarefa = buscarTarefaValida(id);
        tarefaDTO.applyToEntity(tarefa);

        if (tarefaDTO.getIdResponsavel() != null) {
            tarefa.setResponsavel(buscarPessoaValida(tarefaDTO.getIdResponsavel()));
        } else {
            tarefa.setResponsavel(null);
        }

        return TarefaResponseDTO.fromEntity(tarefaRepository.save(tarefa));
    }

    @Override
    @Transactional
    public void excluirTarefa(Long id) {
        TarefaEntity tarefa = buscarTarefaValida(id);

        if (!String.valueOf(tarefa.getStatus()).equals("PENDENTE")) {
            throw new OperacaoNaoPermitidaException(
                    "Só é possível excluir tarefas com status PENDENTE");
        }

        tarefaRepository.delete(tarefa);
    }

    @Override
    public TarefaResponseDTO buscarTarefaPorId(Long id) {
        return TarefaResponseDTO.fromEntity(buscarTarefaValida(id));
    }

    @Override
    public List<TarefaResponseDTO> listarTarefasPorProjeto(Long projetoId) {
        ProjetoEntity projeto = buscarProjetoValido(projetoId);
        return tarefaRepository.findByProjeto(projeto).stream()
                .map(TarefaResponseDTO::fromEntity)
                .toList();
    }

    @Override
    public List<TarefaResponseDTO> listarTarefasPorResponsavel(Long responsavelId) {
        PessoaEntity responsavel = buscarPessoaValida(responsavelId);
        return tarefaRepository.findByResponsavel(responsavel).stream()
                .map(TarefaResponseDTO::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public TarefaResponseDTO atualizarStatus(Long id, TarefaStatusDTO statusDTO) {
        TarefaEntity tarefa = buscarTarefaValida(id);
        validarTransicaoStatus(String.valueOf(tarefa.getStatus()), statusDTO.getNovoStatus());

        statusDTO.applyToEntity(tarefa);

        if (statusDTO.getNovoStatus().equals("CONCLUIDA")) {
            tarefa.setDataConclusao(LocalDateTime.now());
        }

        return TarefaResponseDTO.fromEntity(tarefaRepository.save(tarefa));
    }

    // Métodos privados para validações
    private TarefaEntity buscarTarefaValida(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa não encontrada"));
    }

    private ProjetoEntity buscarProjetoValido(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));
    }

    private PessoaEntity buscarPessoaValida(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa não encontrada"));
    }

    private void validarNovaTarefa(TarefaRequestDTO tarefaDTO) {
        if (tarefaDTO.getIdResponsavel() != null) {
            PessoaEntity responsavel = buscarPessoaValida(tarefaDTO.getIdResponsavel());
            if (!responsavel.isFuncionario()) {
                throw new ValidacaoException("O responsável deve ser um funcionário");
            }
        }
    }

    private void validarTransicaoStatus(String statusAtual, String novoStatus) {
        if (statusAtual.equals("CONCLUIDA") && !novoStatus.equals("CONCLUIDA")) {
            throw new OperacaoNaoPermitidaException(
                    "Tarefa concluída não pode ter status alterado");
        }
    }
}