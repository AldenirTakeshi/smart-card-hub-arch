package com.takeshi.smartcardhub.application.usecase;

import com.takeshi.smartcardhub.application.exception.CartaoNaoEncontradoException;
import com.takeshi.smartcardhub.application.port.out.CardRepository;
import com.takeshi.smartcardhub.domain.Card;
import com.takeshi.smartcardhub.domain.Money;

import java.util.UUID;

public class RealizarCompraUseCase {

    private final CardRepository repository;

    public RealizarCompraUseCase(CardRepository repository) {
        this.repository = repository;
    }

    public void executar(UUID idCartao, Money valor){
        Card cartaoExistente = repository.buscarPorId(idCartao)
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartao nao encontrado: " + idCartao));
        cartaoExistente.comprar(valor);
        repository.salvar(cartaoExistente);
    }
}
