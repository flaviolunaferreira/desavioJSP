package com.portfolio.controller;

import com.portfolio.model.ProjetoEntity;
import com.portfolio.service.ProjetoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/projetos")
@Controller
public class ProjetoViewController {

    @GetMapping("/{id}")
    public String mostrarDetalhesProjeto(@PathVariable Long id) {
            // formalidades -> acesso a pasta views
        return "projeto-detalhes";
    }
}
