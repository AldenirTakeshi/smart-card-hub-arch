package com.takeshi.smartcardhub.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardTest {

    @Test
    void deveNascerAtivo() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));

        assertEquals(CardStatus.ATIVO, card.getStatus());
    }

    @Test
    void deveBloquear() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));

        card.bloquear();

        assertEquals(CardStatus.BLOQUEADO, card.getStatus());
    }

    @Test
    void deveDesbloquear() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));

        card.bloquear();
        card.desbloquear();

        assertEquals(CardStatus.ATIVO, card.getStatus());
    }

    @Test
    void naoDeveDesbloquearCartaoCancelado() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));

        card.cancelar();

        assertThrows(TransicaoDeStatusInvalidaException.class, () -> card.desbloquear());
    }

    @Test
    void naoDeveBloquearCartaoCancelado() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));

        card.cancelar();

        assertThrows(TransicaoDeStatusInvalidaException.class, () -> card.bloquear());
    }

    @Test
    void naoDeveCancelarCartaoCancelado() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));

        card.cancelar();

        assertThrows(TransicaoDeStatusInvalidaException.class, () -> card.cancelar());
    }

    @Test
    void naoDeveDesbloquearCartaoAtivo() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));

        assertThrows(TransicaoDeStatusInvalidaException.class, () -> card.desbloquear());
    }

    @Test
    void deveCancelarCartaoAtivo() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));

        card.cancelar();

        assertEquals(CardStatus.CANCELADO, card.getStatus());
    }

    @Test
    void deveCancelarCartaoBloqueado() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));

        card.bloquear();
        card.cancelar();

        assertEquals(CardStatus.CANCELADO, card.getStatus());
    }

    @Test
    void deveReservarLimiteAoComprar() {
        CreditLine creditLine = new CreditLine(Money.de("1000.00"));
        Card card = new Card(creditLine);

        card.comprar(Money.de("200.00"));

        assertEquals(new BigDecimal("200.00"), creditLine.getReservado().getValor());
    }

    @Test
    void naoDeveComprarComCartaoBloqueado() {
        CreditLine creditLine = new CreditLine(Money.de("1000.00"));
        Card card = new Card(creditLine);

        card.bloquear();

        assertThrows(CartaoInativoException.class, () -> card.comprar(Money.de("300.00")));
        assertEquals(new BigDecimal("1000.00"), creditLine.disponivel().getValor());
    }

    @Test
    void naoDeveComprarComCartaoCancelado() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));

        card.cancelar();

        assertThrows(CartaoInativoException.class, () -> card.comprar(Money.de("300.00")));
    }

    @Test
    void naoDeveComprarSemLimite() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));

        assertThrows(LimiteInsuficienteException.class, () -> card.comprar(Money.de("1000.01")));
    }

    @Test
    void cartaoBloqueadoNaoAfetaOutroCartaoDaMesmaLinha() {
        CreditLine creditLine = new CreditLine(Money.de("1000.00"));

        Card cardFisico = new Card(creditLine);
        Card cardVirtual = new Card(creditLine);

        cardFisico.bloquear();
        cardVirtual.comprar(Money.de("700.00"));

        assertEquals(new BigDecimal("700.00"), creditLine.getReservado().getValor());
    }

}
