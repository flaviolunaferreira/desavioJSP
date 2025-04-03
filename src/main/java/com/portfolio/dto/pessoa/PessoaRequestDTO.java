package com.portfolio.dto.pessoa;

import com.portfolio.model.PessoaEntity;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PessoaRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato 999.999.999-99")
    private String cpf;

    @NotNull(message = "Indicador de funcionário é obrigatório")
    private Boolean funcionario;

    @NotNull(message = "Indicador de gerente é obrigatório")
    private Boolean gerente;

    public PessoaEntity toEntity() {
        PessoaEntity pessoa = new PessoaEntity();
        pessoa.setNome(this.nome);
        pessoa.setDataNascimento(this.dataNascimento);
        pessoa.setCpf(this.cpf);
        pessoa.setFuncionario(this.funcionario);
        pessoa.setGerente(this.gerente);
        return pessoa;
    }
}