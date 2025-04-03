package com.portfolio.repository;

import com.portfolio.model.MembroEntity;
import com.portfolio.model.MembroId;
import com.portfolio.model.PessoaEntity;
import com.portfolio.model.ProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembroRepository extends JpaRepository<MembroEntity, MembroId> {

    @Query("SELECT m FROM MembroEntity m WHERE m.projeto.id = :projetoId AND m.pessoa.id = :pessoaId")
    Optional<MembroEntity> findByIdProjetoIdAndIdPessoaId(Long projetoId, Long pessoaId);

    @Query("SELECT COUNT(m) > 0 FROM MembroEntity m WHERE m.projeto.id = :projetoId AND m.pessoa.id = :pessoaId")
    boolean existsByProjetoAndPessoa(@Param("projetoId") Long projetoId, @Param("pessoaId") Long pessoaId);

    List<MembroEntity> findByProjeto(ProjetoEntity projeto);

    List<MembroEntity> findByPessoa(PessoaEntity pessoa);

    @Query("SELECT COUNT(m) > 0 FROM MembroEntity m WHERE m.pessoa.id = :pessoaId AND m.projeto.status = 'EM_ANDAMENTO'")
    boolean existsByPessoaInProjetoAtivo(Long pessoaId);
}
