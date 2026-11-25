package com.takeshi.smartcardhub.application.usecase;

import com.takeshi.smartcardhub.application.exception.CartaoNaoEncontradoException;
import com.takeshi.smartcardhub.application.port.out.CardRepository;
import com.takeshi.smartcardhub.domain.Card;

import java.util.UUID;

public class BloquearCartaoUseCase {
    private final CardRepository repository;

    public BloquearCartaoUseCase(CardRepository repository) {
        this.repository = repository;
    }

    public void executar(UUID idCard) {
        Card card = repository.buscarPorId(idCard)
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartao nao encontrado: " + idCard));
        card.bloquear();
        repository.salvar(card);
    }
}
