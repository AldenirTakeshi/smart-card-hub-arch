package com.takeshi.smartcardhub.application.usecase;

import com.takeshi.smartcardhub.application.exception.CartaoNaoEncontradoException;
import com.takeshi.smartcardhub.application.port.out.CardRepository;
import com.takeshi.smartcardhub.domain.*;
import com.takeshi.smartcardhub.infrastructure.adapter.out.memoria.CardRepositoryEmMemoria;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RealizarCompraUseCaseTest {

    private CardRepository repository = new CardRepositoryEmMemoria();
    private RealizarCompraUseCase useCase = new RealizarCompraUseCase(repository);

    @Test
    void deveRealizarCompraComCartaoAtivo() {
        CreditLine creditLine = new CreditLine(Money.de("1000.00"));
        Card card = new Card(creditLine);
        repository.salvar(card);

        useCase.executar(card.getId(), Money.de("300.00"));

        Card cardSalvo = repository.buscarPorId(card.getId())
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartao nao encontrado: " + card.getId()));

        assertEquals(new BigDecimal("700.00"), cardSalvo.limiteDisponivel().getValor());
    }

    @Test
    void deveLancarExcecaoQuandoCartaoNaoExiste() {
        assertThrows(CartaoNaoEncontradoException.class, () -> {
            useCase.executar(UUID.randomUUID(), Money.de("300.00"));
        });
    }

    @Test
    void naoDeveComprarComCartaoBloqueado() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));
        card.bloquear();
        repository.salvar(card);

        assertThrows(CartaoInativoException.class, () -> {
            useCase.executar(card.getId(), Money.de("300.00"));
        });
    }

    @Test
    void naoDeveComprarAcimaDoLimite() {
        CreditLine creditLine = new CreditLine(Money.de("1000.00"));
        Card card = new Card(creditLine);
        repository.salvar(card);

        assertThrows(LimiteInsuficienteException.class, () -> {
            useCase.executar(card.getId(), Money.de("1000.01"));
        });
    }
}
