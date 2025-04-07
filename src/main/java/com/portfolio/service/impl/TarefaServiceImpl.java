package com.portfolio.service.impl;

import com.portfolio.dto.tarefa.TarefaStatusDTO;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository tarefaRepository;
    private final ProjetoRepository projetoRepository;
    private final PessoaRepository pessoaRepository;
    private static final Logger log = LoggerFactory.getLogger(TarefaServiceImpl.class);

    @Override
    @Transactional
    public TarefaResponseDTO criarTarefa(TarefaRequestDTO tarefaDTO) {
        log.info("Criando nova tarefa para o projeto: {}", tarefaDTO.getIdProjeto());

        validarNovaTarefa(tarefaDTO);

        TarefaEntity tarefa = tarefaDTO.toEntity();
        tarefa.setProjeto(buscarProjetoValido(tarefaDTO.getIdProjeto()));

        if (tarefaDTO.getIdResponsavel() != null) {
            PessoaEntity responsavel = buscarPessoaValida(tarefaDTO.getIdResponsavel());
            validarResponsavel(responsavel);
            tarefa.setResponsavel(responsavel);
        }

        // Força status inicial como PENDENTE
        tarefa.setStatus(StatusTarefa.PENDENTE);

        TarefaEntity tarefaSalva = tarefaRepository.save(tarefa);
        log.info("Tarefa criada com sucesso: {}", tarefaSalva.getId());

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
        validarTransicaoStatus(tarefa.getStatus(), statusDTO.getNovoStatus());
        statusDTO.applyToEntity(tarefa);

        return TarefaResponseDTO.fromEntity(tarefaRepository.save(tarefa));
    }

    @Override
    public List<TarefaResponseDTO> listarTarefasRecentes(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "dataLimite"));

        return tarefaRepository.findAll(pageable).stream()
                .map(TarefaResponseDTO::fromEntity)
                .collect(Collectors.toList());
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

    private void validarResponsavel(PessoaEntity responsavel) {
        if (!responsavel.isFuncionario()) {
            log.error("Tentativa de atribuir tarefa a não-funcionário: {}", responsavel.getId());
            throw new ValidacaoException("O responsável deve ser um funcionário");
        }
    }

    private void validarTransicaoStatus(StatusTarefa atual, String novoStatus) {
        if (atual == StatusTarefa.CONCLUIDA && !novoStatus.equals("CONCLUIDA")) {
            throw new ValidacaoException("Tarefa concluída não pode ter status alterado");
        }
    }

    @Override
    public List<TarefaResponseDTO> filtrarTarefas(Long projetoId, String status) {
        StatusTarefa statusEnum = (status != null) ? StatusTarefa.valueOf(status) : null;

        List<TarefaEntity> tarefas = tarefaRepository.filtrarPorProjetoEStatus(projetoId, statusEnum);

        return tarefas.stream()
                .map(TarefaResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

}