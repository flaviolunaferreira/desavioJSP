package com.portfolio.service.impl;

import com.portfolio.dto.projeto.ProjetoRequestDTO;
import com.portfolio.dto.projeto.ProjetoResponseDTO;
import com.portfolio.dto.projeto.ProjetoStatusUpdateDTO;
import com.portfolio.exception.*;
import com.portfolio.model.*;
import com.portfolio.model.enumeration.RiscoProjeto;
import com.portfolio.model.enumeration.StatusProjeto;
import com.portfolio.repository.*;
import com.portfolio.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjetoServiceImpl implements ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final PessoaRepository pessoaRepository;


    @Override
    @Transactional
    public ProjetoResponseDTO criarProjeto(ProjetoRequestDTO projetoDTO) {
        validarNovoProjeto(projetoDTO);

        ProjetoEntity projeto = projetoDTO.toEntity();
        projeto.setGerente(buscarPessoaValida(projetoDTO.getIdGerente()));
        projeto.setStatus(StatusProjeto.valueOf("EM_ANALISE"));

        ProjetoEntity projetoSalvo = projetoRepository.save(projeto);
        return ProjetoResponseDTO.fromEntity(projetoSalvo);
    }

    @Override
    @Transactional
    public ProjetoResponseDTO atualizarProjeto(Long id, ProjetoRequestDTO projetoDTO) {
        ProjetoEntity projeto = buscarProjetoValido(id);
        validarAtualizacaoProjeto(projeto, projetoDTO);

        // Atualiza apenas campos permitidos
        projeto.setNome(projetoDTO.getNome());
        projeto.setDataInicio(projetoDTO.getDataInicio());
        projeto.setDataPrevisaoFim(projetoDTO.getDataPrevisaoFim());
        projeto.setDataFim(projetoDTO.getDataFim());
        projeto.setDescricao(projetoDTO.getDescricao());
        projeto.setOrcamento(projetoDTO.getOrcamento());
        projeto.setRisco(RiscoProjeto.valueOf(projetoDTO.getRisco()));

        if (!projeto.getGerente().getId().equals(projetoDTO.getIdGerente())) {
            projeto.setGerente(buscarPessoaValida(projetoDTO.getIdGerente()));
        }

        return ProjetoResponseDTO.fromEntity(projetoRepository.save(projeto));
    }

    @Override
    @Transactional
    public void excluirProjeto(Long id) {
        ProjetoEntity projeto = buscarProjetoValido(id);

        if (!projetoPodeSerExcluido(String.valueOf(projeto.getStatus()))) {
            throw new OperacaoNaoPermitidaException(
                    "Projeto com status " + projeto.getStatus() + " não pode ser excluído");
        }

        projetoRepository.delete(projeto);
    }

    @Override
    public ProjetoResponseDTO buscarProjetoPorId(Long id) {
        ProjetoEntity projeto = buscarProjetoValido(id);
        return ProjetoResponseDTO.fromEntity(projeto);
    }

    @Override
    public List<ProjetoResponseDTO> listarTodosProjetos() {
        return projetoRepository.findAll().stream()
                .map(ProjetoResponseDTO::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public ProjetoResponseDTO atualizarStatus(Long id, ProjetoStatusUpdateDTO statusDTO) {
        ProjetoEntity projeto = buscarProjetoValido(id);
        statusDTO.applyToEntity(projeto);

        // Lógica adicional para status específicos
        if ("CANCELADO".equalsIgnoreCase(statusDTO.getNovoStatus())) {
            projeto.setDataFim(LocalDate.now());
        }

        return ProjetoResponseDTO.fromEntity(projetoRepository.save(projeto));
    }

    @Override
    public String calcularRisco(Long projetoId) {
        ProjetoEntity projeto = buscarProjetoValido(projetoId);

        if (projeto.getDataFim() != null) {
            return "BAIXO"; // Projetos concluídos têm risco baixo
        }

        long diasAtraso = ChronoUnit.DAYS.between(
                projeto.getDataPrevisaoFim(),
                LocalDate.now()
        );

        if (diasAtraso > 30) return "ALTO";
        if (diasAtraso > 15) return "MEDIO";
        return "BAIXO";
    }

    @Override
    public ProjetoEntity buscarProjetoComMembros(Long id) {
        return null;
    }

    // Métodos privados de validação (mantidos iguais)
    private ProjetoEntity buscarProjetoValido(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));
    }

    private PessoaEntity buscarPessoaValida(Long id) {
        PessoaEntity pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa não encontrada"));

        if (!pessoa.isGerente()) {
            throw new ValidacaoException("A pessoa deve ter cargo de gerente");
        }

        return pessoa;
    }

    private void validarNovoProjeto(ProjetoRequestDTO projetoDTO) {
        if (projetoRepository.existsByNome(projetoDTO.getNome())) {
            throw new ConflitoException("Já existe um projeto com este nome");
        }

        if (projetoDTO.getDataInicio().isAfter(projetoDTO.getDataPrevisaoFim())) {
            throw new ValidacaoException("Data de previsão deve ser após a data de início");
        }
    }

    private void validarAtualizacaoProjeto(ProjetoEntity projeto, ProjetoRequestDTO projetoDTO) {
        if (!projeto.getNome().equals(projetoDTO.getNome())) {
            if (projetoRepository.existsByNome(projetoDTO.getNome())) {
                throw new ConflitoException("Já existe um projeto com este nome");
            }
        }

        if (projetoDTO.getDataInicio().isAfter(projetoDTO.getDataPrevisaoFim())) {
            throw new ValidacaoException("Data de previsão deve ser após a data de início");
        }
    }

    @Override
    public Map<String, Long> countProjectsByStatus() {
        List<Object[]> results = projetoRepository.countByStatus();
        return results.stream()
                .collect(Collectors.toMap(
                        arr -> ((StatusProjeto) arr[0]).name(),
                        arr -> (Long) arr[1]
                ));
    }

    @Override
    public Map<String, Long> countProjectsByRisk() {
        List<Object[]> results = projetoRepository.countByRisk();
        return results.stream()
                .collect(Collectors.toMap(
                        arr -> ((RiscoProjeto) arr[0]).name(),
                        arr -> (Long) arr[1]
                ));
    }

    private boolean projetoPodeSerExcluido(String status) {
        return !List.of("INICIADO", "EM_ANDAMENTO", "ENCERRADO").contains(status);
    }
}