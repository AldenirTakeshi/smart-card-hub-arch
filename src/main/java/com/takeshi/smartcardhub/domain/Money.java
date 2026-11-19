package com.takeshi.smartcardhub.domain;

import java.math.BigDecimal;

public class Money {

    private final BigDecimal valor;

    public Money(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor nao pode ser negativo");
        }
        this.valor = valor;
    }
    public static Money de(String valor){
        BigDecimal valorConvertido = new BigDecimal(valor);
        return new Money(valorConvertido);
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Money somar(Money outro) {
        BigDecimal resultado = this.valor.add(outro.valor);
        return new Money(resultado);
    }

    public Money subtrair(Money outro) {
        BigDecimal resultado = this.valor.subtract(outro.valor);
        return new Money(resultado);
    }

    public boolean ehMaiorQue(Money outro) {
        return this.valor.compareTo(outro.valor) > 0;
    }
}

