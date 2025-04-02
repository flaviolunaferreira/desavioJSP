package com.portfolio.repository;

import com.portfolio.model.TarefaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<TarefaEntity, Long> {
}
