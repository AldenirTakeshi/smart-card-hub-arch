package com.takeshi.smartcardhub.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {

    @Test
    void deveSomarDoisValores(){
        Money a = Money.de("100.00");
        Money b = Money.de("50.00");

        Money resultado  = a.somar(b);

        assertEquals(new BigDecimal("150.00"), resultado.getValor());
    }

    @Test
    void deveSubtrairDoisValores(){
        Money valor1 = Money.de("10.00");
        Money valor2 = Money.de("7.00");

        Money resultado  = valor1.subtrair(valor2);

        assertEquals(new BigDecimal("3.00"), resultado.getValor());
    }

    @Test
    void naoDeveSubtrairParaNegativo(){
        Money cinco = Money.de("5.00");
        Money dez = Money.de("10.00");

        assertThrows(IllegalArgumentException.class, () -> cinco.subtrair(dez));
    }

    @Test
    void naoDeveCriarValorNegativo(){
        assertThrows(IllegalArgumentException.class, () -> Money.de("-10.00"));
    }

    @Test
    void deveSubtrairResultadoEmZero(){
        Money valor1 = Money.de("100.00");
        Money valor2 = Money.de("100.00");

        Money resultado = valor1.subtrair(valor2);

        assertEquals(new BigDecimal("0.00"), resultado.getValor());
    }

    @Test
    void deveSerMaiorQuandoValorForMaior(){
        Money dez = Money.de("10.00");
        Money cinco = Money.de("5.00");

        boolean resultado = dez.ehMaiorQue(cinco);

        assertTrue(resultado);
    }

    @Test
    void naoDeveSerMaiorQuandoValorForMenor(){
        Money dez = Money.de("10.00");
        Money cinco = Money.de("5.00");

        boolean resultado = cinco.ehMaiorQue(dez);

        assertFalse(resultado);
    }

    @Test
    void naoDeveSerMaiorQuandoValoresForemIguais(){
        Money dez = Money.de("10.00");
        Money dez2 = Money.de("10.00");

        boolean resultado = dez2.ehMaiorQue(dez);

        assertFalse(resultado);
    }


}
