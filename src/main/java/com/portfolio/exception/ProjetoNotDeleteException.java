package com.portfolio.exception;

public class ProjetoNotDeleteException extends RuntimeException {
    public ProjetoNotDeleteException(String message) {
        super(message);
    }

    public ProjetoNotDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjetoNotDeleteException(Throwable cause) {
        super(cause);
    }
}
