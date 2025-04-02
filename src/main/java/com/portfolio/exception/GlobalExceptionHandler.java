package com.portfolio.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProjetoNotDeleteException.class)
    public ResponseEntity<String> handleProjetoNotDeleteException(ProjetoNotDeleteException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidMemberRoleException.class)
    public ResponseEntity<String> handleInvalidMemberRoleException(InvalidMemberRoleException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(500).body("Erro Interno no Servidor: " + ex.getMessage());
    }

}
