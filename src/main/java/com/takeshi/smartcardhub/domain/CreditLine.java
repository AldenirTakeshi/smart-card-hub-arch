package com.takeshi.smartcardhub.domain;

public class CreditLine {
    private final Money limiteTotal;
    private Money reservado = Money.de("0.00");
    private Money capturado = Money.de("0.00");

    public CreditLine(Money limiteTotal) {
        this.limiteTotal = limiteTotal;
    }

    public Money getCapturado() {
        return capturado;
    }

    public Money getReservado() {
        return reservado;
    }

    public Money disponivel() {
        return limiteTotal.subtrair(reservado).subtrair(capturado);
    }

    public void reservar(Money valor) {
        if (valor.ehMaiorQue(disponivel())){
            throw new  LimiteInsuficienteException("Limite Insuficiente");
        }
        reservado = reservado.somar(valor);
    }

    public void capturar(Money valor) {
        if (valor.ehMaiorQue(reservado)){
            throw new ReservaInsuficienteException("Reserva insuficiente");
        }
        capturado = capturado.somar(valor);
        reservado = reservado.subtrair(valor);
    }

    public void liberar(Money valor){
        if (valor.ehMaiorQue(reservado)){
            throw new ReservaInsuficienteException("Reserva insuficiente");
        }
        reservado = reservado.subtrair(valor);
    }
}
