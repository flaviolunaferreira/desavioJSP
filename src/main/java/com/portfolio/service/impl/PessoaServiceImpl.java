package com.portfolio.service.impl;

import com.portfolio.dto.pessoa.PessoaRequestDTO;
import com.portfolio.dto.pessoa.PessoaResponseDTO;
import com.portfolio.dto.pessoa.PessoaUpdateDTO;
import com.portfolio.exception.*;
import com.portfolio.model.*;
import com.portfolio.repository.*;
import com.portfolio.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    @Override
    @Transactional
    public PessoaResponseDTO criarPessoa(PessoaRequestDTO pessoaDTO) {
        validarNovaPessoa(pessoaDTO);

        PessoaEntity pessoa = pessoaDTO.toEntity();
        PessoaEntity pessoaSalva = pessoaRepository.save(pessoa);

        return PessoaResponseDTO.fromEntity(pessoaSalva);
    }

    @Override
    @Transactional
    public PessoaResponseDTO atualizarPessoa(Long id, PessoaUpdateDTO pessoaDTO) {
        PessoaEntity pessoa = buscarPessoaValida(id);
        pessoaDTO.applyToEntity(pessoa);

        return PessoaResponseDTO.fromEntity(pessoaRepository.save(pessoa));
    }

    @Override
    @Transactional
    public void excluirPessoa(Long id) {
        PessoaEntity pessoa = buscarPessoaValida(id);
        validarExclusaoPessoa(pessoa);

        pessoaRepository.delete(pessoa);
    }

    @Override
    public PessoaResponseDTO buscarPessoaPorId(Long id) {
        return PessoaResponseDTO.fromEntity(buscarPessoaValida(id));
    }

    @Override
    public List<PessoaResponseDTO> listarTodasPessoas() {
        return pessoaRepository.findAll().stream()
                .map(PessoaResponseDTO::fromEntity)
                .toList();
    }

    @Override
    public List<PessoaResponseDTO> listarFuncionarios() {
        return pessoaRepository.findByFuncionarioTrue().stream()
                .map(PessoaResponseDTO::fromEntity).toList();
    }

    @Override
    public List<PessoaResponseDTO> listarGerentes() {
        return pessoaRepository.findByGerenteTrue().stream()
                .map(PessoaResponseDTO::fromEntity)
                .toList();
    }

    // Métodos privados para validações e regras de negócio
    private PessoaEntity buscarPessoaValida(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa não encontrada"));
    }

    private void validarNovaPessoa(PessoaRequestDTO pessoaDTO) {
        // CPF único
        if (pessoaDTO.getCpf() != null &&
                !pessoaDTO.getCpf().isBlank() &&
                pessoaRepository.existsByCpf(pessoaDTO.getCpf())) {
            throw new ConflitoException("CPF já cadastrado");
        }

        // Validação de cargo mútuo
        if (pessoaDTO.getGerente() && pessoaDTO.getFuncionario()) {
            throw new ValidacaoException("Atribua apenas um cargo: Gerente OU Funcionário");
        }
    }


    private void validarExclusaoPessoa(PessoaEntity pessoa) {
        if (pessoa.isGerente() && pessoaRepository.countProjetosComoGerente(pessoa.getId()) > 0) {
            throw new OperacaoNaoPermitidaException(
                    "Não é possível excluir um gerente que está associado a projetos");
        }
    }
}