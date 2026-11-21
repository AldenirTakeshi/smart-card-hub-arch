package com.takeshi.smartcardhub.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CreditLineTest {

    @Test
    void deveTerDisponivelIgualAoLimiteQuandoNovo() {
        CreditLine linha = new CreditLine(Money.de("1000.00"));

        Money disponivel = linha.disponivel();

        assertEquals(new BigDecimal("1000.00"), disponivel.getValor());
    }

    @Test
    void deveDiminuirDisponivelAoReservar() {
        CreditLine linha = new CreditLine(Money.de("1000.00"));

        linha.reservar(Money.de("300.00"));

        assertEquals(new BigDecimal("700.00"), linha.disponivel().getValor());
    }

    @Test
    void devePermitirReservarExatamenteODisponivel() {
        CreditLine linha = new CreditLine(Money.de("1000.00"));

        linha.reservar(Money.de("1000.00"));

        assertEquals(new BigDecimal("0.00"), linha.disponivel().getValor());
    }

    @Test
    void naoDeveReservarAcimaDoDisponivel() {
        CreditLine linha = new CreditLine(Money.de("1000.00"));

        assertThrows(LimiteInsuficienteException.class, () -> linha.reservar(Money.de("1000.01")));
    }

    @Test
    void naoDeveReservarQuandoReservasAnterioresConsumiramOLimite() {
        CreditLine linha = new CreditLine(Money.de("1000.00"));

        linha.reservar(Money.de("900.00"));

        assertThrows(LimiteInsuficienteException.class, () -> linha.reservar(Money.de("900.00")));
    }

    @Test
    void deveManterDisponivelAoCapturar() {
        CreditLine linha = new CreditLine(Money.de("1000.00"));

        linha.reservar(Money.de("300.00"));
        linha.capturar(Money.de("300.00"));

        assertEquals(new BigDecimal("700.00"), linha.disponivel().getValor());
    }

    @Test
    void devePermitirCapturaParcial() {
        CreditLine linha = new CreditLine(Money.de("1000.00"));

        linha.reservar(Money.de("300.00"));
        linha.capturar(Money.de("100.00"));

        assertEquals(new BigDecimal("200.00"), linha.getReservado().getValor());
        assertEquals(new BigDecimal("100.00"), linha.getCapturado().getValor());
    }

    @Test
    void naoDeveCapturarMaisQueOReservado() {
        CreditLine linha = new CreditLine(Money.de("1000.00"));

        linha.reservar(Money.de("1000.00"));

        assertThrows(ReservaInsuficienteException.class, () -> linha.capturar(Money.de("1000.01")));
    }

    @Test
    void deveAumentarDisponivelAoLiberar() {
        CreditLine linha = new CreditLine(Money.de("1000.00"));

        linha.reservar(Money.de("500.00"));
        linha.liberar(Money.de("500.00"));

        assertEquals(new BigDecimal("1000.00"), linha.disponivel().getValor());
    }

    @Test
    void devePermitirLiberarParcial() {
        CreditLine linha = new CreditLine(Money.de("1000.00"));

        linha.reservar(Money.de("500.00"));
        linha.liberar(Money.de("200.00"));

        assertEquals(new BigDecimal("0.00"), linha.getCapturado().getValor());
        assertEquals(new BigDecimal("300.00"), linha.getReservado().getValor());
    }

    @Test
    void naoDeveLiberarMaisQueOReservado() {
        CreditLine linha = new CreditLine(Money.de("1000.00"));

        linha.reservar(Money.de("500.00"));

        assertThrows(ReservaInsuficienteException.class, () -> linha.liberar(Money.de("500.01")));
    }
}
