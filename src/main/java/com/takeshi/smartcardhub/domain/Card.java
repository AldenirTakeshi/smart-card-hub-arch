package com.takeshi.smartcardhub.domain;

public class Card {

    private CardStatus status;

    public Card() {
        this.status = CardStatus.ATIVO;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void bloquear() {
        if (this.status != CardStatus.ATIVO) {
            throw new TransicaoDeStatusInvalidaException("Só e possivel bloquear um cartao ativo");
        }
        this.status = CardStatus.BLOQUEADO;
    }

    public void desbloquear() {
        if (this.status != CardStatus.BLOQUEADO) {
            throw new TransicaoDeStatusInvalidaException("Cartao nao pode ser desbloqueado");
        }
        this.status = CardStatus.ATIVO;
    }

    public void cancelar() {
        if (this.status == CardStatus.CANCELADO) {
            throw new TransicaoDeStatusInvalidaException("Cartão já está cancelado");
        }
        this.status = CardStatus.CANCELADO;
    }
}
