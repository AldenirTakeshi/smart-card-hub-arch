package com.takeshi.smartcardhub.application.usecase;

import com.takeshi.smartcardhub.application.exception.CartaoNaoEncontradoException;
import com.takeshi.smartcardhub.application.port.out.CardRepository;
import com.takeshi.smartcardhub.domain.*;
import com.takeshi.smartcardhub.infrastructure.adapter.out.memoria.CardRepositoryEmMemoria;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BloquearCartaoUseCaseTest {

    private final CardRepository repository = new CardRepositoryEmMemoria();
    private final BloquearCartaoUseCase useCase = new BloquearCartaoUseCase(repository);

    @Test
    void deveBloquearCartaoExistente() {
        Card card = new Card(new CreditLine(Money.de("1000.00")));
        repository.salvar(card);

        useCase.executar(card.getId());

        Card cardSalvo = repository.buscarPorId(card.getId()).orElseThrow();
        assertEquals(CardStatus.BLOQUEADO, cardSalvo.getStatus());
    }

    @Test
    void deveLancarExcecaoQuandoCartaoNaoExiste() {
        assertThrows(CartaoNaoEncontradoException.class, () -> useCase.executar(UUID.randomUUID()));
    }

    @Test
    void naoDeveBloquearCartaoCancelado() {
        CreditLine linha = new CreditLine(Money.de("1000.00"));
        Card card = new Card(linha);
        card.cancelar();
        repository.salvar(card);

        assertThrows(TransicaoDeStatusInvalidaException.class, () -> useCase.executar(card.getId()));
    }
}
