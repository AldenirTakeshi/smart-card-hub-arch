package com.takeshi.smartcardhub.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardTest {

    @Test
    void deveNascerAtivo() {
        Card card = new Card();

        assertEquals(CardStatus.ATIVO, card.getStatus());
    }

    @Test
    void deveBloquear() {
        Card card = new Card();

        card.bloquear();

        assertEquals(CardStatus.BLOQUEADO, card.getStatus());
    }

    @Test
    void deveDesbloquear() {
        Card card = new Card();

        card.bloquear();
        card.desbloquear();

        assertEquals(CardStatus.ATIVO, card.getStatus());
    }

    @Test
    void naoDeveDesbloquearCartaoCancelado() {
        Card card = new Card();

        card.cancelar();

        assertThrows(TransicaoDeStatusInvalidaException.class, () -> card.desbloquear());
    }

    @Test
    void naoDeveBloquearCartaoCancelado() {
        Card card = new Card();

        card.cancelar();

        assertThrows(TransicaoDeStatusInvalidaException.class, () -> card.bloquear());
    }

    @Test
    void naoDeveCancelarCartaoCancelado() {
        Card card = new Card();

        card.cancelar();

        assertThrows(TransicaoDeStatusInvalidaException.class, () -> card.cancelar());
    }

    @Test
    void naoDeveDesbloquearCartaoAtivo() {
        Card card = new Card();

        assertThrows(TransicaoDeStatusInvalidaException.class, () -> card.desbloquear());
    }

    @Test
    void deveCancelarCartaoAtivo() {
        Card card = new Card();

        card.cancelar();

        assertEquals(CardStatus.CANCELADO, card.getStatus());
    }

    @Test
    void deveCancelarCartaoBloqueado() {
        Card card = new Card();

        card.bloquear();
        card.cancelar();

        assertEquals(CardStatus.CANCELADO, card.getStatus());
    }
}
