package com.portfolio.repository;

import com.portfolio.model.PessoaEntity;
import com.portfolio.model.ProjetoEntity;
import com.portfolio.model.enumeration.StatusTarefa;
import com.portfolio.model.TarefaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<TarefaEntity, Long> {
    List<TarefaEntity> findByProjeto(ProjetoEntity projeto);
    List<TarefaEntity> findByResponsavel(PessoaEntity responsavel);
    List<TarefaEntity> findByStatus(StatusTarefa status);
    List<TarefaEntity> findByDataLimiteBetween(LocalDateTime inicio, LocalDateTime fim);
}
