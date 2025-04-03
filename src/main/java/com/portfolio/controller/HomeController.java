package com.portfolio.controller;

import com.portfolio.dto.projeto.ProjetoResponseDTO;
import com.portfolio.service.ProjetoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ProjetoService projetoService;

    public HomeController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("projeto", new ProjetoResponseDTO());
        model.addAttribute("listaProjetos", projetoService.listarTodosProjetos());
        return "index";
    }
}
