package com.takeshi.smartcardhub.application.exception;

public class CartaoNaoEncontradoException extends RuntimeException {
    public CartaoNaoEncontradoException(String message) {
        super(message);
    }
}
