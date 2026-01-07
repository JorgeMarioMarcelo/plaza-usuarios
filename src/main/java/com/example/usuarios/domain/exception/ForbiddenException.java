package com.example.usuarios.domain.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String msg) { super(msg); }
}
