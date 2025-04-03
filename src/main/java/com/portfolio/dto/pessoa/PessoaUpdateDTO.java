package com.portfolio.dto.pessoa;

import com.portfolio.model.PessoaEntity;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PessoaUpdateDTO {

    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    private LocalDate dataNascimento;

    private Boolean funcionario;

    private Boolean gerente;

    public void applyToEntity(PessoaEntity pessoa) {
        if (this.nome != null) {
            pessoa.setNome(this.nome);
        }
        if (this.dataNascimento != null) {
            pessoa.setDataNascimento(this.dataNascimento);
        }
        if (this.funcionario != null) {
            pessoa.setFuncionario(this.funcionario);
        }
        if (this.gerente != null) {
            pessoa.setGerente(this.gerente);
        }
    }
}