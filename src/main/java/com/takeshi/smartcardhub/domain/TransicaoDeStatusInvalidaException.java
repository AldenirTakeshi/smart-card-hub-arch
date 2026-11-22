package com.takeshi.smartcardhub.domain;

public class TransicaoDeStatusInvalidaException extends RuntimeException{
    public TransicaoDeStatusInvalidaException(String message){
        super(message);
    }
}
