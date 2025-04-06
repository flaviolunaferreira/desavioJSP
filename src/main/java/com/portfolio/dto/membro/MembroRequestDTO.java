package com.portfolio.dto.membro;

import com.portfolio.model.MembroEntity;
import com.portfolio.model.PessoaEntity;
import com.portfolio.model.ProjetoEntity;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MembroRequestDTO {

    @NotNull(message = "ID do projeto é obrigatório")
    private Long idProjeto;

    @NotNull(message = "ID da pessoa é obrigatório")
    private Long idPessoa;

    @NotNull(message = "Data de entrada é obrigatória")
    @PastOrPresent(message = "Data de entrada deve ser hoje ou no passado")
    private LocalDate dataEntrada;

    @Size(max = 50, message = "Função deve ter no máximo 50 caracteres")
    private String funcao;

    public MembroEntity toEntity() {
        MembroEntity membro = new MembroEntity();

        ProjetoEntity projeto = new ProjetoEntity();
        projeto.setId(this.idProjeto);
        membro.setProjeto(projeto);

        PessoaEntity pessoa = new PessoaEntity();
        pessoa.setId(this.idPessoa);
        membro.setPessoa(pessoa);

        membro.setDataEntrada(this.dataEntrada);
        membro.setFuncao(this.funcao);

        return membro;
    }
}