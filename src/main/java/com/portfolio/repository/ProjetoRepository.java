package com.portfolio.repository;

import com.portfolio.model.ProjetoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<ProjetoEntity, Long> {
    boolean existsByNome(@NotBlank(message = "Nome é obrigatório") @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres") String nome);

    //método para contagem por status
    @Query("SELECT p.status, COUNT(p) FROM ProjetoEntity p GROUP BY p.status")
    List<Object[]> countByStatus();

    //método para contagem por risco
    @Query("SELECT p.risco, COUNT(p) FROM ProjetoEntity p WHERE p.risco IS NOT NULL GROUP BY p.risco")
    List<Object[]> countByRisk();

}
