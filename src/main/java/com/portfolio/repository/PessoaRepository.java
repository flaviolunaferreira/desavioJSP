package com.portfolio.repository;

import com.portfolio.model.PessoaEntity;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity, Long> {
    boolean existsByCpf(String cpf);
    List<PessoaEntity> findByFuncionarioTrue();
    List<PessoaEntity> findByGerenteTrue();

    @Query("SELECT COUNT(p) FROM ProjetoEntity p WHERE p.gerente.id = :id")
    long countProjetosComoGerente(Long id);
}
