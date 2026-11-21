package com.takeshi.smartcardhub.domain;

public class ReservaInsuficienteException extends RuntimeException{
    public ReservaInsuficienteException(String message){
        super(message);
    }
}
