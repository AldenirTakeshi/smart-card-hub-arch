package com.takeshi.smartcardhub.infrastructure.adapter.out.memoria;

import com.takeshi.smartcardhub.domain.Card;
import com.takeshi.smartcardhub.domain.CreditLine;
import com.takeshi.smartcardhub.domain.Money;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CardRepositoryEmMemoriaTest {

    CardRepositoryEmMemoria repository = new CardRepositoryEmMemoria();

    @Test
    void deveSalvarEBuscarCartao() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));

        repository.salvar(card);
        Optional<Card> resultado = repository.buscarPorId(card.getId());

        assertTrue(resultado.isPresent());
        assertEquals(card.getId(), resultado.get().getId());
    }

    @Test
    void deveRetornarVazioQuandoCartaoNaoExiste() {
        Optional<Card> resultado = repository.buscarPorId(UUID.randomUUID());
        assertTrue(resultado.isEmpty());
    }
}
