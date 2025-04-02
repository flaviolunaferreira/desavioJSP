package com.portfolio.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

// Entidade que representa uma pessoa (gerente ou funcionário)
@Data
@Entity
@Table(name = "pessoa")
public class PessoaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private LocalDate dataNascimento;

    private String cpf;

    private boolean funcionario;

    private boolean gerente;

}