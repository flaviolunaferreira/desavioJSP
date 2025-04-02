package com.portfolio.exception;

public class InvalidMemberRoleException extends RuntimeException {
    public InvalidMemberRoleException(String message) {
        super(message);
    }

    public InvalidMemberRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMemberRoleException(Throwable cause) {
        super(cause);
    }
}
