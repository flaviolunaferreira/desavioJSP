package com.portfolio.service;

import com.portfolio.dto.pessoa.PessoaRequestDTO;
import com.portfolio.dto.pessoa.PessoaResponseDTO;
import com.portfolio.dto.pessoa.PessoaUpdateDTO;
import java.util.List;

public interface PessoaService {
    PessoaResponseDTO criarPessoa(PessoaRequestDTO pessoaDTO);
    PessoaResponseDTO atualizarPessoa(Long id, PessoaUpdateDTO pessoaDTO);
    void excluirPessoa(Long id);
    PessoaResponseDTO buscarPessoaPorId(Long id);
    List<PessoaResponseDTO> listarTodasPessoas();
    List<PessoaResponseDTO> listarFuncionarios();
    List<PessoaResponseDTO> listarGerentes();
}