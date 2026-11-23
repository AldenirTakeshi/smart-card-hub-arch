package com.takeshi.smartcardhub.domain;

public class Card {

    private final CreditLine creditLine;
    private CardStatus status;

    public Card(CreditLine creditLine) {
        this.status = CardStatus.ATIVO;
        this.creditLine = creditLine;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void bloquear() {
        if (this.status != CardStatus.ATIVO) {
            throw new TransicaoDeStatusInvalidaException("Cartão não está ativo. Status atual: " + status);
        }
        this.status = CardStatus.BLOQUEADO;
    }

    public void desbloquear() {
        if (this.status != CardStatus.BLOQUEADO) {
            throw new TransicaoDeStatusInvalidaException("Cartão não está bloqueado. Status atual: " + status);
        }
        this.status = CardStatus.ATIVO;
    }

    public void cancelar() {
        if (this.status == CardStatus.CANCELADO) {
            throw new TransicaoDeStatusInvalidaException("Cartão já está cancelado");
        }
        this.status = CardStatus.CANCELADO;
    }

    public void comprar(Money valor) {
        if (this.status != CardStatus.ATIVO){
            throw new CartaoInativoException("Cartão não está ativo. Status atual: " + status);
        }
        creditLine.reservar(valor);
    }
}
