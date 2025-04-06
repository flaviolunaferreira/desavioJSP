package com.portfolio.service.impl;

import com.portfolio.dto.membro.MembroRequestDTO;
import com.portfolio.dto.membro.MembroResponseDTO;
import com.portfolio.dto.membro.MembroUpdateDTO;
import com.portfolio.exception.*;
import com.portfolio.model.*;
import com.portfolio.model.enumeration.StatusProjeto;
import com.portfolio.repository.*;
import com.portfolio.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembroServiceImpl implements MembroService {

    private final MembroRepository membroRepository;
    private final ProjetoRepository projetoRepository;
    private final PessoaRepository pessoaRepository;
    private static final Logger log = LoggerFactory.getLogger(MembroServiceImpl.class);

    @Override
    @Transactional
    public MembroResponseDTO associarMembro(MembroRequestDTO membroDTO) {
        log.info("Associando membro ao projeto: {}", membroDTO.getIdProjeto());

        validarAssociacao(membroDTO);

        MembroEntity membro = membroDTO.toEntity();
        membro.setProjeto(buscarProjetoValido(membroDTO.getIdProjeto()));
        membro.setPessoa(buscarPessoaValida(membroDTO.getIdPessoa()));

        MembroEntity membroSalvo = membroRepository.save(membro);
        log.info("Membro associado com sucesso: {}", membroSalvo.getPessoa());

        return MembroResponseDTO.fromEntity(membroSalvo);
    }

    @Override
    @Transactional
    public void desassociarMembro(Long projetoId, Long pessoaId) {
        MembroEntity membro = buscarAssociacaoValida(projetoId, pessoaId);
        membroRepository.delete(membro);
    }

    @Override
    @Transactional
    public MembroResponseDTO atualizarFuncao(Long projetoId, Long pessoaId, MembroUpdateDTO updateDTO) {
        MembroEntity membro = buscarAssociacaoValida(projetoId, pessoaId);
        updateDTO.applyToEntity(membro);
        return MembroResponseDTO.fromEntity(membroRepository.save(membro));
    }

    @Override
    public MembroResponseDTO buscarAssociacao(Long projetoId, Long pessoaId) {
        return MembroResponseDTO.fromEntity(buscarAssociacaoValida(projetoId, pessoaId));
    }

    @Override
    public List<MembroResponseDTO> listarMembrosPorProjeto(Long projetoId) {
        ProjetoEntity projeto = buscarProjetoValido(projetoId);
        return membroRepository.findByProjeto(projeto).stream()
                .map(MembroResponseDTO::fromEntity)
                .toList();
    }

    @Override
    public List<MembroResponseDTO> listarProjetosDaPessoa(Long pessoaId) {
        PessoaEntity pessoa = buscarPessoaValida(pessoaId);
        return membroRepository.findByPessoa(pessoa).stream()
                .map(MembroResponseDTO::fromEntity)
                .toList();
    }

    // Métodos privados para validações
    private MembroEntity buscarAssociacaoValida(Long projetoId, Long pessoaId) {
        return membroRepository.findByIdProjetoIdAndIdPessoaId(projetoId, pessoaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Associação não encontrada"));
    }

    private ProjetoEntity buscarProjetoValido(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));
    }

    private PessoaEntity buscarPessoaValida(Long id) {
        PessoaEntity pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa não encontrada"));

        if (!pessoa.isFuncionario()) {
            throw new ValidacaoException("A pessoa deve ser um funcionário para ser associada");
        }

        return pessoa;
    }

    private void validarAssociacao(MembroRequestDTO membroDTO) {
        // Verifica se a associação já existe
        if (membroRepository.existsByProjetoAndPessoa(membroDTO.getIdProjeto(), membroDTO.getIdPessoa())) {
            log.error("Tentativa de associar membro já existente");
            throw new ConflitoException("Esta pessoa já está associada ao projeto");
        }

        ProjetoEntity projeto = buscarProjetoValido(membroDTO.getIdProjeto());

        // Verifica status do projeto
        if (projeto.getStatus() == StatusProjeto.ENCERRADO ||
                projeto.getStatus() == StatusProjeto.CANCELADO) {
            log.error("Tentativa de associar membro a projeto inativo");
            throw new OperacaoNaoPermitidaException(
                    "Não é possível associar membros a projetos encerrados ou cancelados");
        }
    }

}