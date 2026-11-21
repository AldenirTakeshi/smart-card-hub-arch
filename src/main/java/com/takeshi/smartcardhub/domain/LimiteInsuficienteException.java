package com.takeshi.smartcardhub.domain;

public class LimiteInsuficienteException extends RuntimeException {
    public LimiteInsuficienteException(String message) {
        super(message);
    }
}
